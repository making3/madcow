package com.example.mattk.madcow;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mattk.madcow.data.Lift;
import com.example.mattk.madcow.helpers.LiftCalculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LiftCalculatorUnitTest {
    private SharedPreferences sharedPrefs;
    private Context context;
    private LiftCalculator calculator;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void before() throws Exception {
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(sharedPrefs.getInt("squat", 230)).thenReturn(230);
        Mockito.when(sharedPrefs.getInt("bench", 140)).thenReturn(140);
        Mockito.when(sharedPrefs.getInt("row", 145)).thenReturn(145);
        Mockito.when(sharedPrefs.getInt("press", 95)).thenReturn(95);
        Mockito.when(sharedPrefs.getInt("deadlift", 250)).thenReturn(250);
        Mockito.when(sharedPrefs.getFloat("set_interval", 0.125f)).thenReturn(0.125f);
        this.calculator = new LiftCalculator(this.sharedPrefs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeadliftDay() {
        calculator.getMaxWeight(1, 1, Lift.DEADLIFT, 2.5);
    }

    @Test
    public void testFirstDaySquat() {
        int actual = calculator.getMaxWeight(1, 1, Lift.SQUAT, 2.5);
        assertEquals(actual, 230);
    }

    @Test
    public void testFirstDayBench() {
        int actual = calculator.getMaxWeight(1, 1, Lift.BENCH, 2.5);
        assertEquals(actual, 140);
    }

    @Test
    public void testFirstDayRow() {
        int actual = calculator.getMaxWeight(1, 1, Lift.ROW, 2.5);
        assertEquals(actual, 145);
    }

    @Test
    public void testSecondDaySquat() {
        int actual = calculator.getMaxWeight(1, 2, Lift.SQUAT, 2.5);
        assertEquals(actual, 175);
    }

    @Test
    public void testSecondDayPress() {
        int actual = calculator.getMaxWeight(1, 2, Lift.PRESS, 2.5);
        assertEquals(actual, 95);
    }

    @Test
    public void testSecondDayDeadlift() {
        int actual = calculator.getMaxWeight(1, 2, Lift.DEADLIFT, 2.5);
        assertEquals(actual, 250);
    }

    @Test
    public void testThirdDaySquat() {
        int actual = calculator.getMaxWeight(1, 3, Lift.SQUAT, 2.5);
        assertEquals(actual, 235);
    }

    @Test
    public void testThirdDayBench() {
        int actual = calculator.getMaxWeight(1, 3, Lift.BENCH, 2.5);
        assertEquals(actual, 145);
    }

    @Test
    public void testThirdDayRow() {
        int actual = calculator.getMaxWeight(1, 3, Lift.ROW, 2.5);
        assertEquals(actual, 150);
    }

    @Test
    public void testWeekWarmupWeights() {
        final int maxWeight = 230;
        final double plate = 2.5;
        int actual = calculator.getWarmupWeight(maxWeight, 1, plate);
        assertEquals(actual, 200);

        actual = calculator.getWarmupWeight(maxWeight, 2, plate);
        assertEquals(actual, 175);

        actual = calculator.getWarmupWeight(maxWeight, 3, plate);
        assertEquals(actual, 145);

        actual = calculator.getWarmupWeight(maxWeight, 4, plate);
        assertEquals(actual, 115);
    }

    @Test
    public void testThirdWeekFirstDaySquat() {
        int actual = calculator.getMaxWeight(3, 1, Lift.SQUAT, 2.5);
        assertEquals(actual, 240);
    }

    @Test
    public void testThirdWeekFirstDayBench() {
        int actual = calculator.getMaxWeight(3, 1, Lift.BENCH, 2.5);
        assertEquals(actual, 145);
    }

    @Test
    public void testThirdWeekFirstDayRow() {
        int actual = calculator.getMaxWeight(3, 1, Lift.ROW, 2.5);
        assertEquals(actual, 150);
    }

    @Test
    public void testThirdWeekSecondDaySquat() {
        int actual = calculator.getMaxWeight(3, 2, Lift.SQUAT, 2.5);
        assertEquals(actual, 180);
    }

    @Test
    public void testThirdWeekSecondDayPress() {
        int actual = calculator.getMaxWeight(3, 2, Lift.PRESS, 2.5);
        assertEquals(actual, 100);
    }

    @Test
    public void testThirdWeekSecondDayDeadlift() {
        int actual = calculator.getMaxWeight(3, 2, Lift.DEADLIFT, 2.5);
        assertEquals(actual, 265);
    }

    @Test
    public void testThirdWeekThirdDaySquat() {
        int actual = calculator.getMaxWeight(3, 3, Lift.SQUAT, 2.5);
        assertEquals(actual, 250);
    }

    @Test
    public void testThirdWeekThirdDayBench() {
        int actual = calculator.getMaxWeight(3, 3, Lift.BENCH, 2.5);
        assertEquals(actual, 150);
    }

    @Test
    public void testThirdWeekThirdDayRow() {
        int actual = calculator.getMaxWeight(3, 3, Lift.ROW, 2.5);
        assertEquals(actual, 155);
    }




    @Test
    public void testSixthWeekFirstDaySquat() {
        int actual = calculator.getMaxWeight(6, 1, Lift.SQUAT, 2.5);
        assertEquals(actual, 260);
    }

    @Test
    public void testSixthWeekFirstDayBench() {
        int actual = calculator.getMaxWeight(6, 1, Lift.BENCH, 2.5);
        assertEquals(actual, 160);
    }

    @Test
    public void testSixthWeekFirstDayRow() {
        int actual = calculator.getMaxWeight(6, 1, Lift.ROW, 2.5);
        assertEquals(actual, 165);
    }

    @Test
    public void testNinthWeekSecondDaySquat() {
        int actual = calculator.getMaxWeight(9, 2, Lift.SQUAT, 2.5);
        assertEquals(actual, 210);
    }

    @Test
    public void testNinthWeekSecondDayPress() {
        int actual = calculator.getMaxWeight(9, 2, Lift.PRESS, 2.5);
        assertEquals(actual, 115);
    }

    @Test
    public void testNinthWeekSecondDayDeadlift() {
        int actual = calculator.getMaxWeight(9, 2, Lift.DEADLIFT, 2.5);
        assertEquals(actual, 305);
    }

    @Test
    public void testTwelfthWeekThirdDaySquat() {
        int actual = calculator.getMaxWeight(12, 3, Lift.SQUAT, 2.5);
        assertEquals(actual, 310);
    }

    @Test
    public void testTwelfthWeekThirdDayBench() {
        int actual = calculator.getMaxWeight(12, 3, Lift.BENCH, 2.5);
        assertEquals(actual, 190);
    }

    @Test
    public void testTwelfthWeekThirdDayRow() {
        int actual = calculator.getMaxWeight(12, 3, Lift.ROW, 2.5);
        assertEquals(actual, 195);
    }
}