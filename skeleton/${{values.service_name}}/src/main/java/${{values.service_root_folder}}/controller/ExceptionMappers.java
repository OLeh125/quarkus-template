package ${{values.service_root_folder}}.controller;

import ${{values.service_root_folder}}.controller.model.common.JsonError;
import ${{values.service_root_folder}}.exception.CallDbFunctionException;
import ${{values.service_root_folder}}.exception.notfound.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.handler.logging.LogLevel;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Slf4j
public class ExceptionMappers {

    @ServerExceptionMapper
    public Response handle(HttpServerRequest req, WebApplicationException e) {
        prettyPrintException(req, e, LogLevel.WARN);
        return e.getResponse();
    }

    @ServerExceptionMapper
    public Response handle(HttpServerRequest req, IllegalArgumentException e) {
        prettyPrintException(req, e, LogLevel.WARN);
        return errorResponse(Status.BAD_REQUEST, toErrorJson(e));
    }

    @ServerExceptionMapper
    public Response handle(HttpServerRequest req, CallDbFunctionException e) {
        prettyPrintException(req, e, LogLevel.WARN);
        return errorResponse(Status.INTERNAL_SERVER_ERROR, toErrorJson(e));
    }

    @ServerExceptionMapper
    public Response handle(HttpServerRequest req, JsonProcessingException e) {
        prettyPrintException(req, e, LogLevel.WARN);
        return errorResponse(Status.BAD_REQUEST, toErrorJson(e));
    }

    @ServerExceptionMapper
    public Response handle(HttpServerRequest req, NotFoundException e) {
        prettyPrintException(req, e, LogLevel.DEBUG);
        return errorResponse(Status.NOT_FOUND, toErrorJson(e));
    }

    @ServerExceptionMapper
    public Response handle(HttpServerRequest req, Exception e) {
        prettyPrintException(req, e, LogLevel.WARN);
        return errorResponse(Status.INTERNAL_SERVER_ERROR, toErrorJson(e));
    }

    @ServerExceptionMapper
    public Response handle(HttpServerRequest req, ConstraintViolationException e) {
        prettyPrintException(req, e, LogLevel.WARN);
        return errorResponse(Status.BAD_REQUEST, toErrorJson(e));
    }

    private JsonError toErrorJson(Exception e) {
        return new JsonError(
            ZonedDateTime.now(),
            Optional.ofNullable(e.getMessage()).map(Collections::singletonList).orElseGet(Collections::emptyList),
            e.getMessage()
        );
    }

    private void prettyPrintException(HttpServerRequest req, Exception exception, LogLevel level) {
        StringBuilder sb = new StringBuilder();
        sb.append("Request ")
            .append(req.uri())
            .append(" threw exception: ")
            .append(exception.getMessage());
        addGeneralInfo(sb, req);
        addHeaders(sb, req);
        addParameters(sb, req);
        addStacktrace(sb, exception);
        String exceptionMessage = sb.toString();
        switch (level) {
            case ERROR:
                log.error(exceptionMessage);
                break;
            case WARN:
                log.warn(exceptionMessage);
                break;
            case INFO:
                log.info(exceptionMessage);
                break;
            case DEBUG:
                log.debug(exceptionMessage);
                break;
            case TRACE:
                log.trace(exceptionMessage);
                break;
            default:
                log.info(exceptionMessage);
                log.error("Unsupported trace level {}", level);
        }
    }

    private void addGeneralInfo(StringBuilder builder, HttpServerRequest req) {
        builder.append("\n\n** General **")
            .append("\n\tRequest Method ").append(req.method())
            .append("\n\tRemote Address ").append(req.remoteAddress());
    }

    private void addHeaders(StringBuilder builder, HttpServerRequest req) {
        builder.append("\n\n** Request Headers **");
        MultiMap headers = req.headers();
        headers.forEach(h -> builder.append("\n\t").append(h.getKey()).append(": ").append(req.getHeader(h.getKey())));

    }

    private void addParameters(StringBuilder builder, HttpServerRequest req) {
        builder.append("\n\n** Request Parameters **");
        MultiMap params = req.params();
        params.forEach(p -> builder.append("\n\t").append(p.getKey()).append(": ").append(req.getParam(p.getKey())));
    }

    private void addStacktrace(StringBuilder builder, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        builder.append("\n\n** Stacktrace **\n").append(sw);
    }

    private <T> Response errorResponse(Status status, T response) {
        return Response
            .status(status)
            .type(MediaType.APPLICATION_JSON)
            .entity(response)
            .build();
    }

    private JsonError toErrorJson(ConstraintViolationException e) {
        return new JsonError(
            ZonedDateTime.now(),
            e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()),
            e.getMessage()
        );
    }

}