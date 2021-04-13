import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Software.EntersPlasticBagsUsed;

public class EntersPlasticBagsUsedTest {
@Test
public void setTotalPlasticBagsUsed_WithPostiveNumberTest() {
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed();
	int test = 1;
	int expected = 1;
	epbu.setTotalPlasticBagsUsed(test);
	int actual = epbu.getTotalPlasticBagsUsed();
	assertEquals(expected, actual);
}

@Test
public void setTotalPlasticBagsUsed_WithNegativeNumberTest() {
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed();
	int test = -1;
	int expected = 0;
	epbu.setTotalPlasticBagsUsed(test);
	int actual = epbu.getTotalPlasticBagsUsed();
	assertEquals(expected, actual);
}


@Test
public void getTotalPlasticBagsUsed() {
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed();
	int test = 0;
	int expected = 0;
	epbu.setTotalPlasticBagsUsed(test);
	int actual = epbu.getTotalPlasticBagsUsed();
	assertEquals(expected, actual);
}

@Test
public void calculateTotalPlasticBagsUsedPrice() {
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed();
	int testBagAmount = 3;
	double pricePerBag = 0.10;
	double expected = pricePerBag * testBagAmount;
	epbu.setTotalPlasticBagsUsed(testBagAmount);
	double actual = epbu.calculateTotalPlasticBagsUsedPrice();
	assertEquals(expected, actual, 0);
}
}
