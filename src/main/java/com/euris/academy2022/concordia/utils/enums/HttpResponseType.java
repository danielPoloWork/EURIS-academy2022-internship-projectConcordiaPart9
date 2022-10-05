package com.euris.academy2022.concordia.utils.enums;

/**
 * @author Daniel Polo<p>
 * This is a list of Hypertext Transfer Protocol (HTTP) response status codes. Status codes are
 * issued by a server in response to a client's request made to the server. It includes codes from
 * IETF Request for Comments (RFCs), other specifications, and some additional codes used in some
 * common applications of the HTTP. The first digit of the status code specifies one of five
 * standard classes of responses. The message phrases shown are typical, but any human-readable
 * alternative may be provided. Unless otherwise stated, the status code is part of the HTTP/1.1
 * standard (RFC 7231).
 * <p>
 * The Internet Assigned Numbers Authority (IANA) maintains the official registry of HTTP status
 * codes.<p>
 * All HTTP response status codes are separated into five classes or categories. The first digit of
 * the status code defines the class of response, while the last two digits do not have any
 * classifying or categorization role. There are five classes defined by the standard:
 * <p>
 * 1xx informational – the request was received, continuing process
 * 2xx successful – the request was successfully received, understood, and accepted
 * 3xx redirection – further action needs to be taken in order to complete the request
 * 4xx client error – the request contains bad syntax or cannot be fulfilled
 * 5xx server error – the server failed to fulfil an apparently valid request
 * <p>
 * 1xx INFORMATIONAL<p>
 * An informational response indicates that the request was received and understood. It is issued
 * on a provisional basis while request processing continues. It alerts the client to wait for a
 * final response. The message consists only of the status line and optional header fields, and is
 * terminated by an empty line. As the HTTP/1.0 standard did not define any 1xx status codes,
 * servers must not send a 1xx response to an HTTP/1.0 compliant client except under experimental
 * conditions.
 * <p>
 * 2xx SUCCESSFUL<p>
 * This class of status codes indicates the action requested by the client was received, understood,
 * and accepted.
 * <p>
 * 3xx REDIRECTION<p>
 * This class of status code indicates the client must take additional action to complete the
 * request. Many of these status codes are used in URL redirection.
 * A user agent may carry out the additional action with no user interaction only if the method used
 * in the second request is GET or HEAD. A user agent may automatically redirect a request. A user
 * agent should detect and intervene to prevent cyclical redirects.
 * <p>
 * 4xx CLIENT ERROR<p>
 * This class of status code is intended for situations in which the error seems to have been caused
 * by the client. Except when responding to a HEAD request, the server should include an entity
 * containing an explanation of the error situation, and whether it is a temporary or permanent
 * condition. These status codes are applicable to any request method. User agents should display
 * any included entity to the user.
 * <p>
 * 5xx SERVER ERROR<p>
 * Response status codes beginning with the digit "5" indicate cases in which the server is aware
 * that it has encountered an error or is otherwise incapable of performing the request. Except when
 * responding to a HEAD request, the server should include an entity containing an explanation of
 * the error situation, and indicate whether it is a temporary or permanent condition. Likewise,
 * user agents should display any included entity to the user. These response codes are applicable
 * to any request method.
 **/

public enum HttpResponseType {
    /* 1xx informational */
    CONTINUE(
            "100",
            "CONTINUE",
            "The server has received the request headers and the client should proceed to send the request body."),
    SWITCHING_PROTOCOLS(
            "101",
            "SWITCHING_PROTOCOLS",
            "The client has asked the server to switch protocols and the server has agreed to do so."),
    PROCESSING(
            "102",
            "PROCESSING",
            "The server has received and is processing the request, but no response is available yet."),
    EARLY_HINTS(
            "103",
            "EARLY_HINTS",
            "The server is returning some hints before final HTTP message."),
    RESPONSE_IS_STALE(
            "103.1",
            "RESPONSE_IS_STALE",
            "The response provided by a cache is stale."),
    REVALIDATION_FAILED(
            "103.2",
            "REVALIDATION_FAILED",
            "The cache was unable to validate the response, due to an inability to reach the origin server."),
    DISCONNECTED_OPERATION(
            "103.4",
            "DISCONNECTED_OPERATION",
            "The cache is intentionally disconnected from the rest of the network."),
    HEURISTIC_EXPIRATION(
            "103.5",
            "HEURISTIC_EXPIRATION",
            "The cache heuristically chose a freshness lifetime greater than 24 hours and the response's age is greater than 24 hours."),
    MISCELLANEOUS_WARNING(
            "103.6",
            "MISCELLANEOUS_WARNING",
            "Something went wrong."),

    /* 2xx successful */
    OK(
            "200",
            "OK",
            "Successful HTTP request."),
    AUTHORIZED(
            "200.1",
            "AUTHORIZED",
            "Authentication is required and has succeeded or has been provided."),
    CREATED(
            "201",
            "CREATED",
            "The request has been fulfilled, resulting that a new resource has been created."),
    UPDATED(
            "201.1",
            "UPDATED",
            "The request has been fulfilled, resulting that a new resource has been modified."),
    DELETED(
            "201.2",
            "DELETED",
            "The request has been fulfilled, resulting that a new resource has been deleted."),
    FETCHED(
            "201.3",
            "FETCHED",
            "The request has been fulfilled, resulting that a resource has been fetched."),
    PULLED(
            "201.4",
            "PULLED",
            "The request has been fulfilled, resulting that a resource has been pulled."),
    ACCEPTED(
            "202",
            "ACCEPTED",
            "The request has been accepted for processing, but the processing has not been completed."),
    NON_AUTHORITATIVE_INFORMATION(
            "203",
            "NON_AUTHORITATIVE_INFORMATION",
            "The server is a transforming proxy that received a 200 OK from its origin, but is returning a modified version of the origin's response."),
    NO_CONTENT(
            "204",
            "NO_CONTENT",
            "The server successfully processed the request, and is not returning any content."),
    RESET_CONTENT(
            "205",
            "RESET_CONTENT",
            "The server successfully processed the request, asks that the requester reset its document view, and is not returning any content."),
    PARTIAL_CONTENT(
            "206",
            "PARTIAL_CONTENT",
            "The server is delivering only part of the resource due to a range header sent by the client."),
    MULTI_STATUS(
            "207",
            "MULTI_STATUS",
            "An XML message and can contain a number of separate response codes, depending on how many sub-requests were made."),
    ALREADY_REPORTED(
            "208",
            "ALREADY_REPORTED",
            "The members of a DAV binding have already been enumerated in a preceding part of the MULTI_STATUS response, and are not being included again."),
    TRANSFORMATION_APPLIED(
            "208.1",
            "TRANSFORMATION_APPLIED",
            "The proxy has applies any transformation to the representation."),
    IM_USED(
            "226",
            "IM_USED",
            "The server has fulfilled a request for the resource, and the response is a representation of the result of one or more instance-manipulations applied to the current instance."),
    MISCELLANEOUS_PERSISTENT_WARNING(
            "226.1",
            "MISCELLANEOUS_PERSISTENT_WARNING",
            "Something keep going wrong."),

    /* 3xx redirection */
    MULTIPLE_CHOICES(
            "300",
            "MULTIPLE_CHOICES",
            "The client may choose from multiple options for the resource"),
    MOVED_PERMANENTLY(
            "301",
            "MOVED_PERMANENTLY",
            "This and all future requests are directed to the given URI."),
    FOUND(
            "302",
            "FOUND",
            "The request has been fulfilled, resulting in the recovery of a resource."),
    SEE_OTHER(
            "303",
            "SEE_OTHER",
            "The response to the request can be found under another URI using the GET method."),
    NOT_MODIFIED(
            "304",
            "NOT_MODIFIED",
            "The resource has not been modified since the version specified by the request headers."),
    USE_PROXY(
            "305",
            "USE_PROXY",
            "Due security reasons the requested resource is available only through a proxy, the address for which is provided in the response."),
    SWITCH_PROXY(
            "306",
            "SWITCH_PROXY",
            "No longer used."),
    TEMPORARY_REDIRECT(
            "307",
            "TEMPORARY_REDIRECT",
            "The request is repeated with another URI; however, future requests should still use the original URI."),
    PERMANENT_REDIRECT(
            "308",
            "PERMANENT_REDIRECT",
            "This and all future requests are directed to the given URI."),

    /* 4xx client error */
    FAILED(
            "400.1",
            "FAILED",
            "Failed HTTP request."),
    BAD_REQUEST(
            "400",
            "BAD_REQUEST",
            "The server cannot process the request due to an apparent client error."),
    UNAUTHORIZED(
            "401",
            "UNAUTHORIZED",
            "Authentication is required and has failed or has not yet been provided."),
    PAYMENT_REQUIRED(
            "402",
            "PAYMENT_REQUIRED",
            "Payment is required and has failed or has not yet been provided."),
    FORBIDDEN(
            "403",
            "FORBIDDEN",
            "The request contained valid data and was understood by the server, but the server is refusing action due permission restrictions."),
    NOT_CREATED(
            "403.1",
            "NOT_CREATED",
            "The request has not been fulfilled, resulting in a new resource not being created."),
    NOT_UPDATED(
            "403.2",
            "NOT_UPDATED",
            "The request has not been fulfilled, resulting in an old resource not being updated."),
    NOT_DELETED(
            "403.3",
            "NOT_DELETED",
            "The request has not been fulfilled, resulting in a resource not being deleted."),
    NOT_FETCH(
            "403.4",
            "NOT_FETCH",
            "The request has been fulfilled, resulting in a resource not being fetched."),
    NOT_PULLED(
            "403.5",
            "NOT_PULLED",
            "The request has been fulfilled, resulting in a resource not being pulled."),
    NOT_ACCEPTED(
            "403.6",
            "NOT_ACCEPTED",
            "The request has not been accepted for processing."),
    NOT_FOUND(
            "404",
            "NOT_FOUND",
            "The request has not been fulfilled, resulting in a resource not being retrieved."),
    /* 404 sub status codes */
    SITE_NOT_FOUND(
            "404.1",
            "SITE_NOT_FOUND",
            "Site not found."),
    ISAPI_OR_CGI_RESTRICTION(
            "404.2",
            "ISAPI_OR_CGI_RESTRICTION",
            "ISAPI or CGI restriction."),
    MIME_TYPE_RESTRICTION(
            "404.3",
            "MIME_TYPE_RESTRICTION",
            "MIME type restriction."),
    NO_HANDLER_CONFIGURED(
            "404.4",
            "NO_HANDLER_CONFIGURED",
            "No handler configured."),
    DENIED_BY_REQUEST_FILTERING_CONFIGUARTION(
            "404.5",
            "DENIED_BY_REQUEST_FILTERING_CONFIGUARTION",
            "Denied by request filtering configuration."),
    VERB_DENIED(
            "404.6",
            "VERB_DENIED",
            "Verb denied."),
    FILE_EXTENSION_DENIED(
            "404.7",
            "FILE_EXTENSION_DENIED",
            "File extension denied."),
    HIDDEN_NAMESPACE(
            "404.8",
            "HIDDEN_NAMESPACE",
            "Hidden namespace."),
    FILE_ATTRIBUTE_HIDDEN(
            "404.9",
            "FILE_ATTRIBUTE_HIDDEN",
            "File attribute hidden."),
    REQUEST_HEADER_TOO_LONG(
            "404.10",
            "REQUEST_HEADER_TOO_LONG",
            "Request header too long."),
    REQUEST_CONTAINS_DOUBLE_ESCAPE_SEQUENCE(
            "404.11",
            "REQUEST_CONTAINS_DOUBLE_ESCAPE_SEQUENCE",
            "Request contains double escape sequence."),
    REQUEST_CONTAIN_HIGH_BIT_CHARACTERS(
            "404.12",
            "REQUEST_CONTAIN_HIGH_BIT_CHARACTERS",
            "Request contains high-bit characters."),
    CONTENT_LENGTH_TOO_LARGE(
            "404.13",
            "CONTENT_LENGTH_TOO_LARGE",
            "Content length too large."),
    REQUEST_URL_TOO_LONG(
            "404.14",
            "REQUEST_URL_TOO_LONG",
            "Request URL too long."),
    QUERY_STRING_TOO_LONG(
            "404.15",
            "QUERY_STRING_TOO_LONG",
            "Query string too long."),
    DAV_REQUEST_SENT_TO_THE_STATIC_FILE_HANDLER(
            "404.16",
            "DAV_REQUEST_SENT_TO_THE_STATIC_FILE_HANDLER",
            "DAV request sent to the static file handler."),
    DYNAMIC_CONTENT_MAPPED_TO_THE_STATIC_FILE_HANDLER(
            "404.17",
            "DYNAMIC_CONTENT_MAPPED_TO_THE_STATIC_FILE_HANDLER",
            "Dynamic content mapped to the static file handler via a wildcard MIME mapping."),
    QUERY_STRING_SEQUENCE_DENIED(
            "404.18",
            "QUERY_STRING_SEQUENCE_DENIED",
            "Query string sequence denied."),
    DENIED_BY_FILTERING_RULE(
            "404.19",
            "DENIED_BY_FILTERING_RULE",
            "Denied by filtering rule."),
    TOO_MANY_URL_SEGMENTS(
            "404.20",
            "TOO_MANY_URL_SEGMENTS",
            "Too Many URL Segments."),
    METHOD_NOT_ALLOWED(
            "405",
            "METHOD_NOT_ALLOWED",
            "The request method is not supported for the requested resource."),
    NOT_ACCEPTABLE(
            "406",
            "NOT_ACCEPTABLE",
            "The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request."),
    PROXY_AUTHENTICATION_REQUIRED(
            "407",
            "PROXY_AUTHENTICATION_REQUIRED",
            "The client must first authenticate itself with the proxy."),
    REQUESTED_TIMEOUT(
            "408",
            "REQUESTED_TIMEOUT",
            "The server timed out waiting for the request. The client did not produce a request within the time that the server was prepared to wait. The client should repeat the request without modifications at any later time."),
    CONFLICT(
            "409",
            "CONFLICT",
            "The request could not be processed because of conflict in the current state of the resource."),
    GONE(
            "410",
            "GONE",
            "The resource requested was previously in use but is no longer available and will not be available again."),
    LENGTH_REQUIRED(
            "411",
            "LENGTH_REQUIRED",
            "The request did not specify the length of its content, which is required by the requested resource."),
    PRECONDITION_FAILED(
            "412",
            "PRECONDITION_FAILED",
            "The server does not meet one of the preconditions that the requester put on the request header fields."),
    PAYLOAD_TOO_LARGE(
            "413",
            "PAYLOAD_TOO_LARGE",
            "The request is larger than the server is willing or able to process."),
    URI_TOO_LONG(
            "414",
            "URI_TOO_LONG",
            "The URI provided was too long for the server to process."),
    UNSUPPORTED_MEDIA_TYPE(
            "415",
            "UNSUPPORTED_MEDIA_TYPE",
            "The request entity has a media type which the server or resource does not support."),
    RANGE_NOT_SATISFIABLE(
            "416",
            "RANGE_NOT_SATISFIABLE",
            "The client has asked for a portion of the file, but the server cannot supply that portion."),
    EXPECTATION_FAILED(
            "417",
            "EXPECTATION_FAILED",
            "The server cannot meet the requirements of the Expect request-header field."),
    IM_A_TEAPOT(
            "418",
            "IM_A_TEAPOT",
            "This is used as an Easter egg."),
    PAGE_EXPIRED(
            "418.1",
            "PAGE_EXPIRED",
            "The requested page is missing or expired."),
    METHOD_FAILURE(
            "418.2",
            "METHOD_FAILURE",
            "The method in the requested has failed"),
    MISSDIRECTED_REQUEST(
            "421",
            "MISSDIRECTED_REQUEST",
            "The request was directed at a server that is not able to produce a response."),
    UNPROCESSABLE_ENTITY(
            "422",
            "UNPROCESSABLE_ENTITY",
            "The request was well-formed but was unable to be followed due to semantic errors."),
    LOCKED(
            "423",
            "LOCKED",
            "The resource that is being accessed is locked."),
    FAILED_DEPENDENCY(
            "424",
            "FAILED_DEPENDENCY",
            "The request failed because it depended on another request and that request failed."),
    TOO_EARLY(
            "425",
            "TOO_EARLY",
            "The server is unwilling to risk processing a request that might be replayed."),
    UPGRADE_REQUIRED(
            "426",
            "UPGRADE_REQUIRED",
            "The client should switch to a different protocol, given in the Upgrade header field."),
    PRECONDITION_REQUIRED(
            "428",
            "PRECONDITION_REQUIRED",
            "The origin server requires the request to be conditional. Intended to prevent the 'lost update' problem."),
    TOO_MANY_REQUESTS(
            "429",
            "TOO_MANY_REQUESTS",
            "The client has sent too many requests in a given amount of time."),
    REQUEST_HEADER_FIELD_TOO_LARGE(
            "431",
            "REQUEST_HEADER_FIELD_TOO_LARGE",
            "The server is unwilling to process the request because either an individual header field, or all the header fields collectively, are too large."),
    LOGIN_TIME_OUT(
            "4xx",
            "LOGIN_TIME_OUT",
            "The client's session has expired and must log in again."),
    NO_RESPONSE(
            "4xx",
            "NO_RESPONSE",
            "The server cannot return information to the client and closed the connection immediately."),
    RETRY_WITH(
            "4xx",
            "RETRY_WITH",
            "The server cannot honour the request because the user has not provided the required information."),
    BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS(
            "4xx",
            "BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS",
            "Microsoft Windows Parental Controls are turned on and are blocking access to the requested webpage."),
    UNAVAILABLE_FOR_LEGAL_REASONS(
            "451",
            "UNAVAILABLE_FOR_LEGAL_REASONS",
            "A server operator has received a legal demand to deny access to a resource or to a set of resources that includes the requested resource."),
    SSL_CERTIFICATE_ERROR(
            "4xx",
            "SSL_CERTIFICATE_ERROR",
            "The client has provided an invalid client certificate."),
    SSL_CERTIFICATE_REQUIRED(
            "4xx",
            "SSL_CERTIFICATE_REQUIRED",
            "The client certificate is required but has not been provided."),
    HTTP_REQUEST_SENT_TO_HTTPS_PORT(
            "4xx",
            "HTTP_REQUEST_SENT_TO_HTTPS_PORT",
            "The client has made a HTTP request to a port listening for HTTPS requests."),
    INVALID_TOKEN(
            "4xx",
            "INVALID_TOKEN",
            "The requested token is expired or otherwise invalid."),
    TOKEN_REQUIRED(
            "4xx",
            "TOKEN_REQUIRED",
            "The requested token is required but not submitted."),
    CLIENT_CLOSED_REQUEST(
            "4xx",
            "CLIENT_CLOSED_REQUEST",
            "The client has closed the request before the server could send a response."),

    /* 5xx server error */
    NOT_CONTINUE(
            "500.1",
            "NOT_CONTINUE",
            "The server has not received the request headers and the client should not proceed to send the request body."),
    NOT_SWITCHING_PROTOCOLS(
            "500.2",
            "NOT_SWITCHING_PROTOCOLS",
            "The client has asked the server to switch protocols and the server has disagreed to do so."),
    NOT_PROCESSING(
            "500.3",
            "NOT_PROCESSING",
            "The server has received but is not processing the request."),
    INTERNAL_SERVER_ERROR(
            "500",
            "INTERNAL_SERVER_ERROR",
            "Internal server error."),
    NOT_IMPLEMENTED(
            "501",
            "NOT_IMPLEMENTED",
            "The server either does not recognize the request method, or it lacks the ability to fulfil the request."),
    BAD_GATEWAY(
            "502",
            "BAD_GATEWAY",
            "The server was acting as a gateway or proxy and received an invalid response from the upstream server."),
    SERVICE_UNAVAILABLE(
            "503",
            "SERVICE_UNAVAILABLE",
            "The server cannot handle the request because it is overloaded or down for maintenance."),
    GATEWAY_TIMEOUT(
            "504",
            "GATEWAY_TIMEOUT",
            "The server was acting as a gateway or proxy and did not receive a timely response from the upstream server."),
    HTTP_VERSION_NOT_SUPPORTED(
            "505",
            "HTTP_VERSION_NOT_SUPPORTED",
            "The server does not support the HTTP protocol version used in the request."),
    VARIANT_ALSO_NEGOTIATES(
            "506",
            "VARIANT_ALSO_NEGOTIATES",
            "Transparent content negotiation for the request results in a circular reference."),
    INSUFFICIENT_STORAGE(
            "507",
            "INSUFFICIENT_STORAGE",
            "The server is unable to store the representation needed to complete the request."),
    LOOP_DETECTED(
            "508",
            "LOOP_DETECTED",
            "The server detected an infinite loop while processing the request."),
    BANDWIDTH_LIMIT_EXCEEDED(
            "508.1",
            "BANDWIDTH_LIMIT_EXCEEDED",
            "The server has exceeded the bandwidth specified by the server administrator."),
    NOT_EXTENDED(
            "510",
            "NOT_EXTENDED",
            "Further extensions to the request are required for the server to fulfil it."),
    NETWORK_AUTHENTICATION_REQUIRED(
            "511",
            "NETWORK_AUTHENTICATION_REQUIRED",
            "The client needs to authenticate to gain network access."),
    WEB_SERVER_RETURNED_AN_UNKNOWN_ERROR(
            "511.1",
            "WEB_SERVER_RETURNED_AN_UNKNOWN_ERROR",
            "The web server returned an empty, unknown, or unexpected response."),
    WEB_SERVER_IS_DOWN(
            "511.2",
            "WEB_SERVER_IS_DOWN",
            "The web server refused connections."),
    CONNECTION_TIMED_OUT(
            "511.3",
            "CONNECTION_TIMED_OUT",
            "The client timed out contacting the web server."),
    WEB_SERVER_IS_UNREACHABLE(
            "511.4",
            "WEB_SERVER_IS_UNREACHABLE",
            "The request could not reach the web server."),
    A_TIME_OUT_OCCURRED(
            "511.5",
            "A_TIME_OUT_OCCURRED",
            "The request could complete a TCP connection to the web server, but did not receive a timely HTTP response."),
    SSL_HANDSAHKE_FAILED(
            "511.6",
            "SSL_HANDSAHKE_FAILED",
            "The request could not negotiate a SSL/TLS handshake with the web server."),
    INVALID_SSL_CERTIFICATE(
            "511.7",
            "INVALID_SSL_CERTIFICATE",
            "The request could not validate the SSL certificate on the origin web server."),
    RAILGUN_ERROR(
            "511.8",
            "RAILGUN_ERROR",
            "The connection with the Rail-gun server has been interrupted."),
    SITE_IS_OVERLOADED(
            "511.9",
            "SITE_IS_OVERLOADED",
            "The website is overloaded and cannot process the API request."),
    SITE_IS_FROZEN(
            "530",
            "SITE_IS_FROZEN",
            "The website has been frozen due to inactivity."),
    NETWORK_READ_TIME_OUT_ERROR(
            "530.1",
            "NETWORK_READ_TIME_OUT_ERROR",
            "The proxy has noticed a network read timeout behind the proxy to a client."),
    NETWORK_CONNECT_TIME_OUT_ERROR(
            "530.2",
            "NETWORK_CONNECT_TIME_OUT_ERROR",
            "The proxy has noticed a network connect timeout behind the proxy to a client.");

    private final String code;
    private final String label;
    private final String desc;

    HttpResponseType(String code, String label, String desc) {
        this.code = code;
        this.label = label;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }
    public String getLabel() {
        return this.label;
    }
    public String getDesc() {
        return this.desc;
    }
}