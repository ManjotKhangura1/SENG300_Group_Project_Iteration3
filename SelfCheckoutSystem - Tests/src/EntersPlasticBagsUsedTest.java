import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EntersPlasticBagsUsedTest {
@Test
public void setTotalPlasticBagsUsed_WithPostiveNumberTest() {
	int test = 1;
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed(test);
	int expected = 1;
	int actual = epbu.getTotalPlasticBagsUsed();
	assertEquals(expected, actual);
}

@Test
public void setTotalPlasticBagsUsed_WithNegativeNumberTest() {
	int test = -1;
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed(test);
	int expected = 0;
	int actual = epbu.getTotalPlasticBagsUsed();
	assertEquals(expected, actual);
}


@Test
public void getTotalPlasticBagsUsed() {
	int test = 0;
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed(test);
	int expected = 0;
	int actual = epbu.getTotalPlasticBagsUsed();
	assertEquals(expected, actual);
}

@Test
public void calculateTotalPlasticBagsUsedPrice() {
	int testBagAmount = 3;
	EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed(testBagAmount);
	double pricePerBag = 0.10;
	double expected = pricePerBag * testBagAmount;
	double actual = epbu.calculateTotalPlasticBagsUsedPrice();
	assertEquals(expected, actual, 0);
}
}
