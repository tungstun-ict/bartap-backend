package com.tungstun.person.port.web.person;

import com.tungstun.common.web.IdResponse;
import com.tungstun.person.application.person.PersonCommandHandler;
import com.tungstun.person.application.person.PersonQueryHandler;
import com.tungstun.person.application.person.command.CreatePerson;
import com.tungstun.person.application.person.command.DeletePerson;
import com.tungstun.person.application.person.command.UpdatePerson;
import com.tungstun.person.application.person.query.GetPerson;
import com.tungstun.person.application.person.query.ListAllPeopleOfBar;
import com.tungstun.person.domain.person.Person;
import com.tungstun.person.port.web.person.request.CreatePersonRequest;
import com.tungstun.person.port.web.person.request.UpdatePersonRequest;
import com.tungstun.person.port.web.person.response.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars/{barId}/people")
public class PersonController {
    private final PersonCommandHandler commandHandler;
    private final PersonQueryHandler queryHandler;

    public PersonController(PersonCommandHandler commandHandler, PersonQueryHandler queryHandler) {
        this.commandHandler = commandHandler;
        this.queryHandler = queryHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new person",
            description = "A new person is for the bar with the provided id",
            tags = "Person"
    )
    public IdResponse createPerson(
            @PathVariable("barId") Long barId,
            @RequestBody CreatePersonRequest request) {
        Long id = commandHandler.handle(new CreatePerson(barId, request.name(), request.userId()));
        return new IdResponse(id);
    }

    @PutMapping("{personId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update person",
            description = "The person is for the bar with the provided id is updated",
            tags = "Person"
    )
    public IdResponse updatePerson(
            @PathVariable("barId") Long barId,
            @PathVariable("personId") Long personId,
            @RequestBody UpdatePersonRequest request) {
        Long id = commandHandler.handle(new UpdatePerson(personId, barId, request.name()));
        return new IdResponse(id);
    }

    @DeleteMapping("{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete person",
            description = "The person is with the given id is deleted",
            tags = "Person"
    )
    public void deletePerson(
            @PathVariable("barId") Long barId,
            @PathVariable("personId") Long personId) {
        commandHandler.handle(new DeletePerson(personId, barId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all people from bar",
            description = "The people of the bar with given the id is queried",
            tags = "Person"
    )
    public List<PersonResponse> getAllPeople(@PathVariable("barId") Long barId) {
        return queryHandler.handle(new ListAllPeopleOfBar(barId))
                .parallelStream()
                .map(PersonResponse::from)
                .toList();
    }

    @GetMapping("/{personId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get person",
            description = "The person with given the id is queried",
            tags = "Person"
    )
    public PersonResponse getPerson(
            @PathVariable("barId") Long barId,
            @PathVariable("personId") Long personId) {
        Person person = queryHandler.handle(new GetPerson(personId, barId));
        return PersonResponse.from(person);
    }
}
