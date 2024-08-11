package com.snakegame.chaujiayi_source_code_javafx_mvn;

/**
 * The {@code SpikeFactory} class is created based on the implementation for the design factory pattern. It represents a factory for creating instances of different types of spikes
 * in the Snake Game. It provides a method to obtain an object of type {@link Spike} based on the specified
 * spike type.
 *
 * @author JiaYiChau
 */
public class SpikeFactory {

    // Private static instance variable
    private static SpikeFactory instance;

    /**
     * Private constructor for the purpose of Singleton Design Pattern.
     * Use {@link #getInstance()} to get the instance of the class.
     */
    private SpikeFactory() {}

    /**
     * Returns the instance of the {@code SpikeFactory} class.
     * If the instance does not exist, it is created (lazy initialization).
     *
     * @return The instance of the {@code SpikeFactory} class.
     */
    public static SpikeFactory getInstance() {
        // Lazy initialization: create the instance if it doesn't exist yet
        if (instance == null) {
            instance = new SpikeFactory();
        }
        return instance;
    }

    /**
     * Gets an object of type {@link Spike} based on the specified spike type.
     *
     * @param spikeType The type of spike to create. Valid values include "FOOD", "WALL", "BOMB", and "SPELLS".
     * @return An object of type {@link Spike} corresponding to the specified spike type.
     *         Returns {@code null} if the specified spike type is invalid or not supported.
     */
    public Spike getSpike(String spikeType){
        if(spikeType == null){
            return null;
        }
        if(spikeType.equalsIgnoreCase("FOOD")){
            return new Food();
        }
        if(spikeType.equalsIgnoreCase("WALL")){
            return new Wall();
        }
        if(spikeType.equalsIgnoreCase("BOMB")){
            return new Bomb();
        }
        if(spikeType.equalsIgnoreCase("SPELLS")){
            return new Spells();
        }

        return null;
    }
}
