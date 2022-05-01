package by.runets.hedgehog.producer;

public interface EventProducer<T> {
    void fireEvent(T event);
}
