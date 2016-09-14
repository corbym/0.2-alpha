package org.groovymud.utils;
/* Copyright 2008 Matthew Corby-Eaglen
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0 
*
* Unless required by applicable law or agreed to in writing, software 
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and 
* limitations under the License. 
*/
import java.util.List;
import java.util.Random;

public class Dice {

    int numberOfDice;
    int dieMax;

    public Dice(int numberOfDice, int dieMax) {
        this.numberOfDice = numberOfDice;
        this.dieMax = dieMax;
    }

    public Dice(String str, List mixedList) {
        System.out.println("mixedList" + mixedList);
    }

    public int roll() {
        int roll = 0;
        Random rnd = new Random(System.currentTimeMillis());
        for (int x = 0; x < numberOfDice; x++) {
            roll += rnd.nextInt(dieMax) + 1;
        }
        return roll;
    }
}
