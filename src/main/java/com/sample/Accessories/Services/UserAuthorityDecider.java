package com.sample.Accessories.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl.Essence;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

import com.sample.Accessories.DTO.UserCreationRequest;

@Service
public class UserAuthorityDecider{
	
	private static final String ldapContextSourceURL = "ldap://localhost:8389";
	
	private static final String dnForUser = "dc=springframework,dc=org";
	
	private static final String userDn = "ou=people";
	
	private static final String userId = "uid";
	
	private static final String groupBase = "ou=groups";
	
	private static final String ACCOUNT_OWNER = "ROLE_ACCOUNT_OWNER";
	
	private static final String DEVICE_USER = "ROLE_DEVICE_USER";
	
	
	public boolean isAuthorizedToCreateUser(Collection<GrantedAuthority> authoritesOfPresentUser, ArrayList<String> authoritiesRequested) {
	 boolean isAuthorized = false;
	 
	 Iterator<GrantedAuthority>	authoritesIterator = authoritesOfPresentUser.iterator();
	 while(authoritesIterator.hasNext()) {
		 SimpleGrantedAuthority authority = (SimpleGrantedAuthority)authoritesIterator.next();
		 if(authority.getAuthority().equals(ACCOUNT_OWNER)){
			 if(authoritiesRequested.size() == 1 && authoritiesRequested.contains(DEVICE_USER)){
				 isAuthorized = true;
				 break;
			 }
		 }
	 }
	 return isAuthorized;
	}

	public void createUser(UserCreationRequest userCreationRequest) {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl(ldapContextSourceURL);
		ldapContextSource.setBase(dnForUser);
		ldapContextSource.afterPropertiesSet();
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(String authority : userCreationRequest.getAuthoritiesRequested()){
			authorities.add(new SimpleGrantedAuthority(authority));
		}
		
		//UserDetails newUser = new User(userCreationRequest.getUsername(), userCreationRequest.getPassword(), true, 
		//		true, true, true, authorities);
		//DirContextAdapter adapter = new DirContextAdapter();
		//adapter.setDn(dnForUser);
		//adapter.setAttributeValues("objectclass", new String[] {"top", "person"});
		
		DirContextOperations adapter = new DirContextAdapter();
		//dnForUser = "cn="+userCreationRequest.getUsername()+dnForUser;
		adapter.setAttributeValue("uid", userCreationRequest.getUsername());
		adapter.setAttributeValue("userPassword", userCreationRequest.getPassword());
		adapter.setAttributeValue("cn", userCreationRequest.getCustomerName());
		adapter.setAttributeValue("sn", userCreationRequest.getSurName());
		//adapter.setAttributeValue("authorites", authorities);
		//LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource);
		//ldapTemplate.bind(adapter);
		Essence InetOrgEsssence = new InetOrgPerson.Essence(adapter);
		InetOrgEsssence.setAuthorities(authorities);
		InetOrgPerson newUser = (InetOrgPerson) (InetOrgEsssence)
			      .createUserDetails();
		LdapUserDetailsManager ldapUserDetailsManager = new LdapUserDetailsManager(ldapContextSource);
		ldapUserDetailsManager.setUsernameMapper(new DefaultLdapUsernameToDnMapper(userDn,userId));
		ldapUserDetailsManager.setGroupSearchBase(groupBase);
	    ldapUserDetailsManager.createUser(newUser);
	}

	public boolean validateRequest(UserCreationRequest userCreationRequest) {
		if(userCreationRequest.getAuthoritiesRequested().contains(DEVICE_USER) &&
				userCreationRequest.getAuthoritiesRequested().contains(ACCOUNT_OWNER)) {
			return true;
		}
		return false;
	}
	
	public String getUserRole(Authentication authentication) {
		String userRole = null;
		if(authentication instanceof AnonymousAuthenticationToken)
			return userRole;
		else {
			if(getAuthoritiesForExistinguser((Collection<GrantedAuthority>)(((LdapUserDetailsImpl)authentication.getPrincipal()).getAuthorities())).equals(ACCOUNT_OWNER))
				return ACCOUNT_OWNER;
			else
				return DEVICE_USER;
		}
	}

	public String getAuthoritiesForExistinguser(Collection<GrantedAuthority> authoritesOfPresentUser) {
		
		Iterator<GrantedAuthority>	authoritesIterator = authoritesOfPresentUser.iterator();
		String authority = null;
		 while(authoritesIterator.hasNext()) {
			 SimpleGrantedAuthority authorities = (SimpleGrantedAuthority)authoritesIterator.next();
			 if(authorities.getAuthority().equals(ACCOUNT_OWNER))
				 authority = ACCOUNT_OWNER;
			 else
				 authority = DEVICE_USER;
		 }
		 return authority;
	}
}
