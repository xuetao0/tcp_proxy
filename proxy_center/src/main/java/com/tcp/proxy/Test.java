package com.tcp.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName Test
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/4 12:23
 **/
public class Test {
    //    今天每日一题：第一年农场有1只成熟的母牛A，往后的每年：①每一只成熟的母牛都会生一只母牛 ②每一只新出生的母牛都在出生的第三年成熟
//③每一只母牛永远不会死 。请问N年后牛的数量是多少 ？
    public static void car() {
        Pasture pasture = new Pasture();
        int n = 11;
        Car a = new Car();
        a.setRipe();
        pasture.addCar(a);
        for (int i = 2; i < n + 1; i++) {
            pasture.incrYear();
        }
        System.out.println(pasture.getCount());
    }

    public static void main(String[] args) {
        int i = 0;
        while (i>0){
            System.out.println(i);
            i--;
        }
//        do {
//            System.out.println(i);
//            i--;
//        } while (i > 0);
    }

    static class Car {
        private int age = 1;
        private boolean isRipe = false;

        public void setRipe() {
            isRipe = true;
        }

        public void incrAge() {
            age++;
            if (age == 3) {
                isRipe = true;
            }
        }

        public boolean isRipe() {
            return isRipe;
        }
    }

    static class Pasture {
        List<Car> cars = new ArrayList<>();
        List<Car> newCars = new ArrayList<>();

        public void addCar(Car car) {
            cars.add(car);
        }

        public void incrYear() {
            Iterator<Car> iterator = cars.iterator();
            while (iterator.hasNext()) {
                Car nextCar = iterator.next();
                nextCar.incrAge();
                if (nextCar.isRipe) {
                    newCar();
                }
            }
            cars.addAll(newCars);
            newCars = new ArrayList<>();
        }

        public int getCount() {
            return cars.size();
        }

        private void newCar() {
            Car car = new Car();
            newCars.add(car);
        }
    }
}
