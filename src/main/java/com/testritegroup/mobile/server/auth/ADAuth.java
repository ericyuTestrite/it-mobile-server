package com.testritegroup.mobile.server.auth;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.testritegroup.mobile.server.route.model.UserAdProfile;

public class ADAuth {
	Logger logger = LoggerFactory.getLogger(ADAuth.class);

	public UserAdProfile authenticate(AuthPassword authPassword, String adServerUrl, String domain){
		UserAdProfile profile = null;
		try {
			profile = nsLookup(domain, adServerUrl, authPassword);
			logger.info(profile.getUserId() + " authenticated ok.");
			
		} catch (Throwable throwable) {
			logger.info(authPassword.getUsername() + " authenticated failed.");
			logger.error(throwable.getMessage());
			return profile;
		}
		return profile;
	}
	
	

	private UserAdProfile nsLookup(String argDomain, 
			String adServerUrl, 
			AuthPassword authPassword) throws NamingException {
		UserAdProfile profile = null;
		try {
			Hashtable env = new Hashtable();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.PROVIDER_URL, "ldap://"+adServerUrl);
	        env.put(Context.SECURITY_PRINCIPAL, authPassword.getUsername()+"@"+ argDomain);
	        env.put(Context.SECURITY_CREDENTIALS, authPassword.getPassword());
	        
	        //will throw exception if any authentication error
	        LdapContext ctx = new InitialLdapContext(env,null);
	        
	        //succcess
	        profile = new UserAdProfile();
			profile.setUserId(authPassword.getUsername());
			
	        NamingEnumeration<?> namingEnum = 
	        		ctx.search("OU=TRG,OU=zAccount,DC=testritegroup,DC=com", 
	        		"(&(objectclass=organizationalPerson)(cn=*"+authPassword.getUsername().toUpperCase()+"*))", 
	        		getSimpleSearchControls());
	        
	        int i =0;
	        while (namingEnum.hasMore ()) {
	        	i++;
	            SearchResult result = (SearchResult) namingEnum.next ();    
	            Attributes attrs = result.getAttributes ();
	            profile.setCompany(getAttributeValue(attrs, "company"));
	            profile.setDepartment(getAttributeValue(attrs, "department"));
	            profile.setDisplayName(getAttributeValue(attrs, "displayName"));
	            profile.setEmail(getAttributeValue(attrs, "mail"));
	            profile.setTitle(getAttributeValue(attrs, "title"));
	            profile.setTel(getAttributeValue(attrs, "telephoneNumber"));
	            profile.setCo(getAttributeValue(attrs, "co"));
	        } 
	        namingEnum.close();
		} catch (NamingException exp) {
			/* AD ERROR CODES
	         525 - user not found
	         52e - invalid credentials
	         530 - not permitted to logon at this time
	         532 - password expired
	         533 - account disabled
	         701 - account expired
	         773 - user must reset password
			 */
			logger.error(exp.getMessage(),exp);
			throw exp;
		}
		return profile;
	}
	
	private String getAttributeValue(Attributes attrib,String key){
		String attribStr = (String)attrib.get(key).toString();
		String attribs[] = attribStr.split(":");
		return attribs[1].trim();
	}
	

	private SearchControls getSimpleSearchControls() {
	    SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    searchControls.setTimeLimit(30000);
	    searchControls.setCountLimit(1002);
	    //String[] attrIDs = {"objectGUID"};
	    //searchControls.setReturningAttributes(attrIDs);
	    return searchControls;
	}
}
