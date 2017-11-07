package com.yb.retreiveandsave.tagidrules;

public class RegexConstants {

	public static final String REGEX_HOSTNAME_RULE_ONE = "(?i)(http|https)://([a-zA-Z.-]+)";
	public static final String REGEX_HOSTNAME_RULE_TWO = "(?i)(http|https)://([0-9.:-]+)/";
	public static final String REGEX_JSON_RULE_ONE = "/([a-zA-Z0-9]+)$";
	public static final String REGEX_JSON_RULE_TWO = "/([a-zA-Z0-9]+)/([a-zA-Z0-9]+)$";
	public static final String REGEX_JSON_RULE_THREE = "(?i)resources/(.+)$";
	public static final String REGEX_XML_RULE_ONE = "((?i)msgname)=\"([a-zA-Z]+)\"";
	public static final String REGEX_XML_RULE_TWO = "</([a-zA-Z:]+)></([a-zA-Z:-]*(?i)body)>";
	public static final String REGEX_XML_RULE_THREE = "</([a-zA-Z:]+)>$";
	public static final String REGEX_XML_URL_RULE = "(?i)soapservlet/([a-zA-Z0-9]+)$";
}
