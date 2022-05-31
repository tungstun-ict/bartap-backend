package com.tungstun.sharedlibrary.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface extended from <code>UserDetails</code> to include extra information<br>
 *  needed for user authentication and identification<br>
 * */
public interface BartapUserDetails extends UserDetails {
    /**
     * Returns the identity used to authenticate the user of type <code>Long</code>. Cannot return
     * <code>null</code>.
     * @return the username (never <code>null</code>)
     */
    Long getIdentity();
}
