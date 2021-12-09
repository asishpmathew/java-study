# java-8

Common intermediate operations
- Map()
- Filter()
- sorted()
- limit()
- Skip()
- flatmap() : multi output

Common terminal operations
- foreach
- collect
    - Collectors.counting()
    -  Collectors.averagingInt(v->v)
    -  Collectors.joining("-","==","==")
    -  Collectors.collectingAndThen(Collectors.summingInt(v->(int)v),result->result/2)
- Reduce : Optional<T> reduce(BinaryOperator<T> accumulator):
- count
- allMatch()
- nonMatch()
- anyMatch()
- min() : .min(Comparator.comparing(Employee::getAge));
- max() : .max(Comparator.comparing(Employee::getAge))
