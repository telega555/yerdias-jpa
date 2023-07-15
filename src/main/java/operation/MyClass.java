package operation;

import java.util.concurrent.atomic.AtomicLong;

public class MyClass {
    private static final AtomicLong idGenerator = new AtomicLong(1);
    private long id;

    public MyClass() {
        // Генерация идентификатора типа long при создании объекта
        this.id = idGenerator.getAndIncrement();
    }

    public long getId() {
        return id;
    }

    // Другие поля и методы класса
}
