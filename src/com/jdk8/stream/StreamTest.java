package com.jdk8.stream;

import com.jdk8.entity.Person;
import com.jdk8.optional.User;
import org.junit.Test;

import javax.lang.model.element.VariableElement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author: 梁艳涛
 * @Title: StreamTest
 * @ProjectName: jdk8
 * @Description:
 * @date: 2022/4/1 16:50
 */
public class StreamTest {
    @Test
    public void Test1() {
        // 1、通过 java.util.Collection.stream() 方法用集合创建流
        List<String> list = Arrays.asList("a", "b", "c");
// 创建一个顺序流
        Stream<String> stream = list.stream();
// 创建一个并行流
        Stream<String> parallelStream = list.parallelStream();


        //2、使用java.util.Arrays.stream(T[] array)方法用数组创建流
        int[] array = {1, 3, 5, 6, 8};
        IntStream stream1 = Arrays.stream(array);

        //3、使用Stream的静态方法：of()、iterate()、generate()
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6);

    }

    @Test
    public void Test2() {
        // 根据起始值seed(0)，每次生成一个指定递增值（n+3）的数，limit(5)用于截断流的长度，即只获取前5个元素。
        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(5);
        stream2.forEach(System.out::println);
        /*
        generate方法返回一个无限连续的无序流，
        其中每个元素由提供的供应商(Supplier)生成。
        generate方法用于生成常量流和随机元素流
        */
        //static <T> Stream<T> generate​(Supplier<? extends T> s)
        //参数：传递生成流元素的供应商(Supplier)。
        //返回：它返回一个新的无限顺序无序的流(Stream)。
        Stream<Double> stream3 = Stream.generate(Math::random).limit(1);
        stream3.forEach(System.out::println);

    }

    @Test
    public void Test3() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Optional<Integer> findFirst = list.stream().parallel().filter(x -> x > 6).findFirst();
        System.out.println(findFirst);
    }

    @Test
    public void Test4() {

        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);

        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        long count = list.stream().filter(x -> x > 6).count();
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
        System.out.println("大于6的值个数：" + count);
    }

    @Test
    public void Test5() {

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        List<String> fiterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName)
                .collect(Collectors.toList());
        System.out.print("高于8000的员工姓名：" + fiterList);
    }

    @Test
    public void Test6() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");

        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());
    }

    @Test
    public void Test7() {
        List<Integer> list = Arrays.asList(7, 6, 9, 4, 11, 6);

        // 自然排序
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max2 = list.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max.get());
        System.out.println("自定义排序的最大值：" + max2.get());
    }

    @Test
    public void Test8() {

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        Optional<Person> max = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("员工工资最大值：" + max.get().getSalary());

    }

    @Test
    public void Test9() {
      /*
      3.4 映射(map/flatMap)
        映射，可以将一个流的元素按照一定的映射规则映射到另一个流中。分为map和flatMap：
        map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。

        */
        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());

        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);
    }

    @Test
    public void Test10() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        // 不改变原来员工集合的方式
        List<Person> personListNew = personList.stream().map(person -> {
            Person personNew = new Person(person.getName(), 0, 0, null, null);
            personNew.setSalary(person.getSalary() + 10000);
            return personNew;
        }).collect(Collectors.toList());
        System.out.println("一次改动前：" + personList.get(0).getName() + "-->" + personList.get(0).getSalary());
        System.out.println("一次改动后：" + personListNew.get(0).getName() + "-->" + personListNew.get(0).getSalary());

        // 改变原来员工集合的方式
        List<Person> personListNew2 = personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 10000);
            return person;
        }).collect(Collectors.toList());
        System.out.println("二次改动前：" + personList.get(0).getName() + "-->" + personListNew.get(0).getSalary());
        System.out.println("二次改动后：" + personListNew2.get(0).getName() + "-->" + personListNew.get(0).getSalary());

        // 改变原来员工集合的方式
        personList.stream().forEach(person -> {
            person.setSalary(person.getSalary() + 10000);
        });
        System.out.println("san次改动前：" + personList.get(0).getName() + "-->" + personListNew.get(0).getSalary());
        System.out.println("san次改动后：" + personList.get(0).getName() + "-->" + personListNew.get(0).getSalary());
    }

    @Test
    public void Test11() {
        List<String> list = Arrays.asList("m-k-l-a", "1-3-5-7");
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split("-");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }

    /*
    在 Java 8 中，Stream.reduce()合并流的元素并产生单个值。

    T reduce(T identity, BinaryOperator<T> accumulator);
  IntStream.java

  int reduce(int identity, IntBinaryOperator op);
  LongStream.java

  long reduce(int identity, LongBinaryOperator op);
  identity = 默认值或初始值。
  BinaryOperator = 函数式接口，取两个值并产生一个新值。（注： java Function 函数中的 BinaryOperator 接口用于执行 lambda 表达式并返回一个 T 类型的返回值）
  1.2 如果缺少identity参数，则没有默认值或初始值，并且它返回 optional。
  Stream.java

  Optional<T> reduce(BinaryOperator<T> accumulator);

    */
    @Test
    public void Test12() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 求和方式1
        Optional<Integer> sum = list.stream().reduce((x, y) -> x + y);
        // 求和方式2
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        // 求和方式3    0是初始化值
        Integer sum3 = list.stream().reduce(0, Integer::sum);

        // 求乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);

        // 求最大值方式1
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        // 求最大值写法2
        Integer max2 = list.stream().reduce(1, Integer::max);

        System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max2);
    }

    @Test
    public void Test13() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        // 求工资之和方式1：
        Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        // 求工资之和方式2：
        Integer sumSalary2 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(),
                (sum1, sum2) -> sum1 + sum2);
        // 求工资之和方式3：
        Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                Integer::max);
        // 求最高工资方式2：
        Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                (max1, max2) -> max1 > max2 ? max1 : max2);

        System.out.println("工资之和：" + sumSalary.get() + "," + sumSalary2 + "," + sumSalary3);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2);

    }

    @Test
    public void Test14() {

   /*     3.6 收集(collect)
        collect，收集，可以说是内容最繁多、功能最丰富的部分了。从字面上去理解，就是把一个流收集起来，最终可以是收集成一个值也可以收集成一个新的集合。
        collect主要依赖java.util.stream.Collectors类内置的静态方法。
        3.6.1 归集(toList/toSet/toMap)
        因为流不存储数据，那么在流中的数据完成处理后，需要将流中的数据重新归集到新的集合里。toList、toSet和toMap比较常用，另外还有toCollection、toConcurrentMap等复杂一些的用法。
        下面用一个案例演示toList、toSet和toMap：*/
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));

        Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000)
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("toList:" + listNew);
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + map);
    }

    @Test
    public void Test15() {

       /*
       3.6.2 统计(count/averaging)
        Collectors提供了一系列用于数据统计的静态方法：
        计数：count
        平均值：averagingInt、averagingLong、averagingDouble
        最值：maxBy、minBy
        求和：summingInt、summingLong、summingDouble
        统计以上所有：summarizingInt、summarizingLong、summarizingDouble
        */
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        // 求总数
        Long count = personList.stream().collect(Collectors.counting());
        // 求平均工资
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        // 求最高工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        // 求工资之和
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));
        // 一次性统计所有信息
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);
    }

    @Test
    public void Test16() {
       /*
       3.6.3 分组(partitioningBy/groupingBy)
        分区：将stream按条件分为两个Map，比如员工按薪资是否高于8000分为两部分。
        分组：将集合分为多个Map，比如员工按性别分组。有单级分组和多级分组。
        */
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 30, "male", "New York"));
        personList.add(new Person("Jack", 7000, 50, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 20, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 60, "female", "New York"));
        personList.add(new Person("Owen", 9500, 80, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 40, "female", "New York"));

        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        // 将员工按性别分组
        Map<String, List<Person>> group = personList.stream().collect(Collectors.groupingBy(Person::getSex));
        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);
    }

    @Test
    public void Test17() {
      /*  3.6.4 接合(joining)
        joining可以将stream中的元素用特定的连接符（没有的话，则直接连接）连接成一个字符串。*/
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining("="));
        System.out.println("所有员工的姓名：" + names);
        List<String> list = Arrays.asList("A", "B", "C");
        String string = list.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);
    }

    @Test
    public void Test18() {
   /*   3.6.5 归约(reducing)
      Collectors类提供的reducing方法，相比于stream本身的reduce方法，增加了对自定义归约的支持。*/
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        // 每个员工减去起征点后的薪资之和（这个例子并不严谨，但一时没想到好的例子）
        Integer sum = personList.stream().collect(Collectors.reducing(0, Person::getSalary, (i, j) -> (i + j - 5000)));
        System.out.println("员工扣税薪资总和：" + sum);

        // stream的reduce
        Optional<Integer> sum2 = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        System.out.println("员工薪资总和：" + sum2.get());
    }

    @Test
    public void Test20() {
      /*
       3.7 排序(sorted)
       sorted，中间操作。有两种排序：
       sorted()：自然排序，流中元素需实现Comparable接口
       sorted(Comparator com)：Comparator排序器自定义排序*/
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Sherry", 9000, 24, "female", "New York"));
        personList.add(new Person("Tom", 8900, 22, "male", "Washington"));
        personList.add(new Person("Jack", 9000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 8800, 26, "male", "New York"));
        personList.add(new Person("Alisa", 9000, 26, "female", "New York"));

        // 按工资升序排序（自然排序）
        List<String> newList = personList.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getName)
                .collect(Collectors.toList());
        // 按工资倒序排序
        List<String> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName).collect(Collectors.toList());
        // 先按工资再按年龄升序排序
        List<String> newList3 = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName)
                .collect(Collectors.toList());
        // 先按工资再按年龄自定义排序（降序）
        List<String> newList4 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());

        System.out.println("按工资升序排序：" + newList);
        System.out.println("按工资降序排序：" + newList2);
        System.out.println("先按工资再按年龄升序排序：" + newList3);
        System.out.println("先按工资再按年龄自定义降序排序：" + newList4);
    }

    @Test
    public void Test21() {
    /*
    3.8 提取/组合
      流也可以进行合并、去重、限制、跳过等操作。
      */
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"d", "e", "f", "g"};

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据
        List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + collect);
        System.out.println("skip：" + collect2);
    }



    @Test
    public void t2() {
        List<User> list = new ArrayList<>();
        User user1 = new User("zhangsan1",30);
        User user2 = new User("zhangsan2",  40);
        User user3 = new User("lisi1",  35);
        User user4 = new User("lisi", 28);
        User user5 = new User("lisim", 32);
        list.add(user1); list.add(user2);list.add(user3); list.add(user4);list.add(user5);
        System.out.println("原始数据："+list);
        //判断姓名是否有重复,练习使用java8的stream方法
        //方法1. distinct, 直接比较大小，只知道是否有重复
        List<String> collect1 = list.stream().map(User::getUserName).distinct().collect(Collectors.toList());
        System.out.println(collect1.size()!=list.size()?"方法1-姓名有重复":"无重复");
        //方法2.用户姓名计数
        Map<Object, Long> collect2 = list.stream().collect(
                Collectors.groupingBy( User::getUserName , Collectors.counting()  )   );
        System.out.println("姓名重复计数情况："+collect2);
        //筛出有重复的姓名
        List<Object> collect3 = collect2.keySet().stream().
                filter(key -> collect2.get(key) > 1).collect(Collectors.toList());
        //可以知道有哪些姓名有重复
        System.out.println("方法2-重复的姓名 ： "+collect3);
        //方法3，对重复的姓名保留计数
        List<Map<String, Long>> collect4 = collect2.keySet().stream().
                filter(key -> collect2.get(key) > 1).map(key -> {
            Map<String, Long> map = new HashMap<>();
            map.put((String) key, collect2.get(key));
            return map;
        }).collect(Collectors.toList());
        System.out.println("方法3-重复的姓名及计数："+collect4);
    }

    @Test
    public void w() {
        String wo="工作";
        String[] w = wo.replace(",", ",").split(",");

        Set<String> wokedaySet=new HashSet<>();
        wokedaySet.add("周六");
        wokedaySet.add("周日");
        wokedaySet.add("工作日");
        Optional<String> any = Arrays.stream(w).filter(r-> wokedaySet.contains(r)
        ).findAny();
        System.out.println(any.isPresent());
        System.out.println(any);
    }


}
