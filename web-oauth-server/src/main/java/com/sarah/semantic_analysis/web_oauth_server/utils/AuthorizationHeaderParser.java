package com.sarah.semantic_analysis.web_oauth_server.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * The class AuthorizationHeaderParser.
 * 
 * @author chandan
 */
@Component
public class AuthorizationHeaderParser {
	/**
	 * SEPARATORS
	 */
	private static final String SEPARATORS = "()<>@,;:\\\\\"/\\[\\]?={} \t";
	/**
	 * TOKEN_PATTERN
	 */
	private static final Pattern TOKEN_PATTERN = Pattern
			.compile("[[\\p{ASCII}]&&[^" + SEPARATORS + "]&&[^\\p{Cntrl}]]+");
	/**
	 * EQ_PATTERN
	 */
	private static final Pattern EQ_PATTERN = Pattern.compile("=");
	/**
	 * TOKEN_QUOTED_PATTERN
	 */
	private static final Pattern TOKEN_QUOTED_PATTERN = Pattern.compile("\"([^\"]|\\\\\\p{ASCII})*\"");
	/**
	 * COMMA_PATTERN
	 */
	private static final Pattern COMMA_PATTERN = Pattern.compile(",");
	/**
	 * LWS_PATTERN
	 */
	private static final Pattern LWS_PATTERN = Pattern.compile("(\r?\n)?[ \t]+");
	/**
	 * The class Tokenizer
	 */
	private static class Tokenizer {
		/**
		 * remaining
		 */
		private String remaining;

		/**
		 * Tokenizer
		 * 
		 * @param input
		 */
		public Tokenizer(String input) {
			remaining = input;
		}

		/**
		 * skipSpaces
		 * 
		 */
		private void skipSpaces() {
			Matcher m = LWS_PATTERN.matcher(remaining);
			if (!m.lookingAt()) {
				return;
			}
			String match = m.group();
			remaining = remaining.substring(match.length());
		}

		/**
		 * match
		 * 
		 * @param p
		 * @return String
		 */
		public String match(Pattern p) {
			skipSpaces();
			Matcher m = p.matcher(remaining);
			if (!m.lookingAt()) {
				return null;
			}
			String match = m.group();
			remaining = remaining.substring(match.length());
			return match;
		}

		/**
		 * mustMatch
		 * 
		 * @param p
		 * @return String
		 */
		public String mustMatch(Pattern p) {
			String match = match(p);
			if (match == null) {
				throw new NoSuchElementException();
			}
			return match;
		}

		/**
		 * hasMore
		 * 
		 * @return boolean
		 */
		public boolean hasMore() {
			skipSpaces();
			return remaining.length() > 0;
		}

	}

	/**
	 * parse
	 * 
	 * @param input
	 * @return Map<String, String>
	 */
	public Map<String, String> parse(String input) {
		Tokenizer t = new Tokenizer(input);
		Map<String, String> map = new HashMap<String, String>();

		String authScheme = t.match(TOKEN_PATTERN);

		while (true) {
			while (t.match(COMMA_PATTERN) != null) {
			}

			if (!t.hasMore()) {
				break;
			}

			String key = t.mustMatch(TOKEN_PATTERN);
			t.mustMatch(EQ_PATTERN);
			String value = t.match(TOKEN_PATTERN);
			if (value == null) {
				value = t.mustMatch(TOKEN_QUOTED_PATTERN);
				value = value.substring(1, value.length() - 1);
			}

			map.put(key, value);

			if (t.hasMore()) {
				t.mustMatch(COMMA_PATTERN);
			}

		}
		return map;
	}

}
