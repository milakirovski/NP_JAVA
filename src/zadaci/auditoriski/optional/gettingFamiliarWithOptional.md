# Getting familiar with Optional in Java 8

The purpose of the class is to provide a type-level solution for representing optional values instead of null references.

Link of the site: https://www.oracle.com/technical-resources/articles/java/java8-optional.html

`of()` = If soundcard were null, a NullPointerException would be immediately thrown.

    SoundCard soundcard = new Soundcard();
    Optional<Soundcard> sc = Optional.of(soundcard);

`ofNullable()`, you can create an Optional object that may hold a null value,
If soundcard were null, the resulting Optional object would be empty.

    Optional<Soundcard> sc = Optional.ofNullable(soundcard);

`ifPresent()` - You no longer need to do an explicit null check; it is enforced by the type system. If the Optional object were empty, nothing would be printed.

    Optional<Soundcard> soundcard = ...;
    soundcard.ifPresent(System.out::println);

`isPresent()` method to find out whether a value is present in an Optional object. In addition, there's a `get()` method that returns the value contained in the Optional object, if it is present. Otherwise, it throws a NoSuchElementException. The two methods can be combined, as follows, to prevent exceptions:

    if(soundcard.isPresent()){
    System.out.println(soundcard.get());
    }

`orElse()` method, which provides a default value if Optional is empty:

    Soundcard soundcard = maybeSoundcard.orElse(new Soundcard("defaut"));

`orElseThrow()` method, which instead of providing a default value if Optional is empty, throws an exception:
    
    Soundcard soundcard =
    maybeSoundCard.orElseThrow(IllegalStateException::new);