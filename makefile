TARGET = MandelbrotPanel
OBJECTS = Complex.class

.PHONY: all run clean

all: $(OBJECTS) $(TARGET).class
run: all
	java $(TARGET)
clean:
	rm *.class

%.class: %.java
	javac $<
