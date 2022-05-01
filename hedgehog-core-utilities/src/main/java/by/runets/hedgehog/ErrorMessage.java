package by.runets.hedgehog;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private int status;
    private String cause;
    private String stackTrace;

    public ErrorMessage(int status, String cause, String stackTrace) {
        this.status = status;
        this.cause = cause;
        this.stackTrace = stackTrace;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String body) {
        this.cause = body;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return status == that.status && Objects.equals(cause, that.cause) && Objects.equals(stackTrace, that.stackTrace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, cause, stackTrace);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "status=" + status +
                ", cause='" + cause + '\'' +
                '}';
    }
}
