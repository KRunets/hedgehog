package by.runets.hedgehog.sender;

public class Message {
    private String message;
    private String title;
    private int priority;

    public Message() {
    }

    public Message(MessageBuilder messageBuilder) {
        this.message = messageBuilder.message;
        this.priority = messageBuilder.priority;
        this.title = messageBuilder.title;
    }

    public String getMessage() { return message; }
    public int getPriority() { return priority; }
    public String getTitle() { return title; }

    public static class MessageBuilder {

        private String message;
        private String title;
        private int priority;

        public MessageBuilder message(String message) {
            this.message = message;
            return this;
        }
        public MessageBuilder title(String title) {
            this.title = title;
            return this;
        }
        public MessageBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
