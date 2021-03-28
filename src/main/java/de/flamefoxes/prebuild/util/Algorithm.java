package de.flamefoxes.prebuild.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Algorithm {
  public static String generate(int length) {
    int len = length - 1;
    List<String> arylist = new ArrayList<String>();
    for (int i = 0; i < len; i++) {
      arylist
        .add(String.valueOf(Math.floor(Math.random() * 10)).replace(".0", ""));
    }
    int num = Integer.valueOf(arylist.stream().collect(Collectors.joining("")));
    return num + "" + checksum(num);
  }

  public static int checksum(int num) {
    if (num == 0) {
      return 0;
    }
    LinkedList<Integer> stack = split(num);
    stack = reverseLinkedList(stack);
    LinkedList<Integer> stackMod = new LinkedList<Integer>();
    stack.stream().map(WithIndex.indexed()).forEachOrdered(e -> {
      stackMod.push(e.index() % 2 == 0 ? e.value() * 2 : e.value());
    });

    Integer sum = stackMod.stream().reduce(0, (tmp, v) -> {
      return tmp + sum(v);
    });

    int check = 10 - (sum % 10);
    return check == 10 ? 0 : check;
  }

  public static LinkedList<Integer> reverseLinkedList(
    LinkedList<Integer> llist) {
    for (int i = 0; i < llist.size() / 2; i++) {
      Integer temp = llist.get(i);
      llist.set(i, llist.get(llist.size() - i - 1));
      llist.set(llist.size() - i - 1, temp);
    }

    // Return the reversed arraylist
    return llist;
  }

  public static LinkedList<Integer> split(int num) {
    LinkedList<Integer> stack = new LinkedList<Integer>();
    while (num > 0) {
      stack.push(num % 10);
      num = num / 10;
    }
    return stack;
  }

  public static Integer sum(int num) {
    Optional<Integer> opt = split(num).stream().reduce((c, v) -> {
      return c + v;
    });
    if (opt.isPresent()) {
      return opt.get();
    } else {
      return 0;
    }
  }
}

class WithIndex<T> {

  private int index;
  private T value;

  WithIndex(int index, T value) {
    this.index = index;
    this.value = value;
  }

  public int index() {
    return index;
  }

  public T value() {
    return value;
  }

  @Override
  public String toString() {
    return value + "(" + index + ")";
  }

  public static <T> Function<T, WithIndex<T>> indexed() {
    return new Function<T, WithIndex<T>>() {
      int index = 0;

      @Override
      public WithIndex<T> apply(T t) {
        return new WithIndex<>(index++, t);
      }
    };
  }
}