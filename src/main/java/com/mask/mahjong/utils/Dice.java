package com.mask.mahjong.utils;

import java.util.Random;

public class Dice {
    public static int[] getDicePoint(int amount){
        int[] dices = new int[amount];
        Random random = new Random();
        for(int i = 0;i < amount;i++) {
            dices[i] = random.nextInt()%7 + 1;
        }
        return dices;
    }

    public static int getDicePointTotal(int amount) {
        Random random = new Random();
        return random.nextInt()%((amount * 6) + 1) + 1;
    }
}
