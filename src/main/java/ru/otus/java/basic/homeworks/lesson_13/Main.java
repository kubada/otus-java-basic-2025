package ru.otus.java.basic.homeworks.lesson_13;

public class Main {
    static void main() {
        Human human = new Human("Исаак");

        Car car = new Car();
        Horse horse = new Horse();
        Bicycle bicycle = new Bicycle(human);
        AllTerrainVehicle allTerrainVehicle = new AllTerrainVehicle();

        human.move(1, TerrainType.PLAIN);
        human.setTransport(allTerrainVehicle);

        allTerrainVehicle.move(100, TerrainType.SWAMP);

        human.leaveTransport(allTerrainVehicle);
        human.setTransport(horse);

        horse.move(25, TerrainType.PLAIN);

        human.leaveTransport(car);

        human.setTransport(car);

        human.leaveTransport(horse);

        human.setTransport(car);

        car.move(300, TerrainType.PLAIN);

        human.leaveTransport(car);
        human.setTransport(bicycle);

        bicycle.move(200, TerrainType.PLAIN);
    }
}
