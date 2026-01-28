package ru.otus.java.basic.homeworks.lesson_13;

public class Main {
    static void main(String[] args) {
        Human human = new Human("Исаак");
        Car car = new Car();
        Horse horse = new Horse();
        AllTerrainVehicle allTerrainVehicle = new AllTerrainVehicle();
        Bicycle bicycle = new Bicycle(human);

        human.move(1, TerrainType.PLAIN);

        car.move(10, TerrainType.PLAIN);

        human.setTransport(car);
        human.move(50, TerrainType.PLAIN);
        human.move(10, TerrainType.SWAMP);
        human.move(10, TerrainType.DENSE_FOREST);
        human.move(200, TerrainType.PLAIN);

        human.setTransport(horse);
        human.leaveTransport(car);
        human.leaveTransport(horse);

        human.setTransport(horse);
        human.move(25, TerrainType.DENSE_FOREST);
        human.move(10, TerrainType.SWAMP);
        human.move(50, TerrainType.PLAIN);
        human.leaveTransport(horse);

        human.setTransport(allTerrainVehicle);
        human.move(20, TerrainType.SWAMP);
        human.move(20, TerrainType.DENSE_FOREST);
        human.move(20, TerrainType.PLAIN);
        human.move(100, TerrainType.PLAIN);
        human.leaveTransport(allTerrainVehicle);

        human.setTransport(bicycle);
        human.move(10, TerrainType.PLAIN);
        human.move(5, TerrainType.SWAMP);
        human.move(200, TerrainType.PLAIN);
        human.leaveTransport(bicycle);

        human.move(10, TerrainType.PLAIN);
        human.move(100, TerrainType.PLAIN);

        human.leaveTransport(car);
    }
}