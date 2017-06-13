package org.sarah.web.client.constants;
/**
 * The interface Constants.
 * @author chandan
 */
public interface Constants {
	/**
	 * The interface Authentication.
	 */
	public interface Authentication {
		String OAUTH_PROVIDER = "oauth_provider";
		String OAUTH_VERSION = "oauth_version";
		String OAUTH_VERIFIER = "oauth_verifier";
		String OAUTH_RESOURCE = "oauth_resource";
		String OAUTH_USERROLE = "oauth_userrole";
		String OAUTH_TOKEN = "oauth_token";
		String USER_ROLE = "user_role";
		String ERROR = "error";
		String ERROR_DESCRIPTION = "error_description";
		String CODE = "code";
		String STATE = "state";
		String DAPSPACE = "semanticanalysis";
		String REDIRECT_URI = "redirect_uri";
		String CODE_OK = "200";
		String COOKIE_PATH = "/";
	}
	/**
	 * The interface Cassandra.
	 */
	public interface Cassandra {
		String TOKEN_HISTORY_KEY = "TempTokenHistory";
	}
	/**
	 * The interface OAuthProvider.
	 */
	public interface OAuthProvider {
		String FACEBOOK = "facebook";
		String LINKEDIN = "linkedin";
	}
	/**
	 * The interface App.
	 */
	public interface App {
		String SEMANTIC_ACCESS_TOKEN = "s_a_t";
		String SEMANTIC_USER_ID = "s_a_id";
		String OAUTH_URL="oAuthUrl";
	}
	/**
	 * The enum OAUTH_VERSION.
	 */
	public enum OAUTH_VERSION {
		OAUTH1, OAUTH2
	}
}
