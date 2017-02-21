/*
 * libajp13 - Pair.java
 *
 * Copyright (c) 2017 Luca Carettoni - Doyensec LLC. 
 * Copyright (c) 2010 Espen Wiborg
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.doyensec.ajp13;

/**
 * Pair consisting of two elements; utility class used for headers and
 * attributes
 * <p>
 * Alternatively, you can use Apache Commons
 */
public class Pair<T, U>
{

    final T a;
    final U b;

    /**
     * Pair constructor
     *
     * @param T Left element
     * @param U Right element
     */
    public Pair(T a, U b)
    {
        this.a = a;
        this.b = b;
    }

    /**
     * Create a new Pair given the left and right elements
     *
     * @param K Left element
     * @param V Right element
     * @return Instance of Pair
     */
    public static <K, V> Pair<K, V> make(K k, V v)
    {
        return new Pair<>(k, v);
    }
}
