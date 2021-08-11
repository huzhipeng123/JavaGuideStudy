package com.java.guide.enums;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

public class Pizza {

    private static EnumSet<PizzaStatus> undeliveredPizzaStatuses =
            EnumSet.of(PizzaStatus.ORDERED, PizzaStatus.READY);

    private PizzaStatus status;

    public static String getJsonString(Pizza pz) {
       return JSON.toJSONString(pz);
    }

    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum PizzaStatus {
        ORDERED (5){
            @Override
            public boolean isOrdered() {
                return true;
            }
        },
        READY (2){
            @Override
            public boolean isReady() {
                return true;
            }
        },
        DELIVERED (0){
            @Override
            public boolean isDelivered() {
                return true;
            }
        };

        private int timeToDelivery;

        public boolean isOrdered() {return false;}

        public boolean isReady() {return false;}

        public boolean isDelivered(){return false;}

        @JsonProperty("timeToDelivery")
        public int getTimeToDelivery() {
            return timeToDelivery;
        }

        PizzaStatus (int timeToDelivery) {
            this.timeToDelivery = timeToDelivery;
        }
    }

    public boolean isDeliverable() {
        return this.status.isReady();
    }

    public void printTimeToDeliver() {
        System.out.println("Time to delivery is " +
                this.getStatus().getTimeToDelivery());
    }

    public PizzaStatus getStatus(){
        return this.status;
    }

    public static List<Pizza> getAllUndeliveredPizzas(List<Pizza> input) {
        return input.stream().filter(
                (s) -> undeliveredPizzaStatuses.contains(s.getStatus()))
                .collect(Collectors.toList());
    }

    public static Map<PizzaStatus,List<Pizza>> groupPizzaByStatus(List<Pizza> pizzaList){
//        EnumMap<PizzaStatus, List<Pizza>> pzByStatus = new EnumMap<PizzaStatus, List<Pizza>>(PizzaStatus.class);
////        Iterator<Pizza> iterator = pizzaList.iterator();
////        while (iterator.hasNext()) {
////            Pizza pz = iterator.next();
////            PizzaStatus status = pz.getStatus();
////            if (pzByStatus.containsKey(status)) {
////                pzByStatus.get(status).add(pz);
////            } else {
////                List<Pizza> newPzList = new ArrayList<>();
////                newPzList.add(pz);
////                pzByStatus.put(status, newPzList);
////            }
////        }
////        return pzByStatus;
        EnumMap<PizzaStatus, List<Pizza>> map = pizzaList.stream().collect(
                Collectors.groupingBy(Pizza::getStatus, () -> new EnumMap<>(PizzaStatus.class), Collectors.toList())
        );
        return map;
    }

    public void deliver() {
        if (isDeliverable()) {
            PizzaDeliverySystemConfiguration.getInstance().getDeliveryStrategy()
                    .deliver(this);
            this.setStatus(PizzaStatus.DELIVERED);
        }
    }
}
