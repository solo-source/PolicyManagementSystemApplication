
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.Test;
//import org.junit.Test;

public class SkeletonTest {

	@Test
	public void skeletonCheckTest() {

		validateClassName("com.service.OrderServiceImpl");
		validateClassName("com.service.RestaurantServiceImpl");
		validateClassName("com.entities.Orders");
		validateClassName("com.entities.Restaurant");
		validateClassName("com.exception.InvalidOrderException");
		validateClassName("com.exception.InvalidRestaurantException");
		
		
		validateMethodSignature("updatePaymentMethod:com.entities.Orders:int;java.lang.String,viewOrdersByRestaurantName:java.util.List:java.lang.String,cancelOrder:com.entities.Orders:int,viewOrdersByStatus:java.util.List:java.lang.String,addOrder:com.entities.Orders:com.entities.Orders;int","com.service.OrderServiceImpl");
	
		validateMethodSignature("updateRestaurantRating:com.entities.Restaurant:int;double,viewRestaurantsByRatingAndLocation:java.util.List:double;java.lang.String,getOrderCountRestaurantWise:java.util.Map,viewRestaurantById:com.entities.Restaurant:int,addRestaurant:com.entities.Restaurant:com.entities.Restaurant","com.service.RestaurantServiceImpl");
	}

	public boolean validateConstructor(String className, Class<?>[] type) {

		boolean iscorrect = false;

		Class classObj = null;
		try {
			classObj = Class.forName(className);

		} catch (ClassNotFoundException e) {
			fail("No class with the name " + className + " as per the problem statement");
		}

		try {
			Constructor<?> constructor;
			constructor = classObj.getConstructor(type);
			iscorrect = true;

			// Object[] obj = { "D1", "Pranav", "B+", "pranav123@gmail.com", "9123791285",
			// "BLR", LocalDate.now().minusDays(30), 10};
			// donor = (Donor)(constructor.newInstance(obj));

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			fail("Check the availability of the constructor in " + className + " class is as per the requirement");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			fail("Check the availability of the constructor in " + className + " class is as per the requirement");

		}
		return iscorrect;

	}

	protected final boolean validateClassName(String className) {

		boolean iscorrect = false;
		try {
			Class.forName(className);
			iscorrect = true;
			// LOG.info("Class Name " + className + " is correct");

		} catch (ClassNotFoundException e) {
			// LOG.log(Level.SEVERE, "You have changed either the " + "class name/package.
			// Use the correct package "+ "and class name as provided in the skeleton");
			fail("You have changed either the " + "class name/package. Use the correct package "
					+ "and class name as provided in the skeleton");

		} catch (Exception e) {
			// LOG.log(Level.SEVERE,"There is an error in validating the " + "Class Name.
			// Please manually verify that the "+ "Class name is same as skeleton before
			// uploading");
			fail("There is an error in validating the " + "Class Name. Please manually verify that the "
					+ "Class name is same as skeleton before uploading");
		}
		return iscorrect;
	}

	protected final void validateMethodSignature(String methodWithExcptn, String className) {
		Class cls = null;
		try {

			String[] actualmethods = methodWithExcptn.split(",");
			boolean errorFlag = false;
			String[] methodSignature;
			String methodName = null;
			String returnType = null;
			String argument[] = null;

			for (String singleMethod : actualmethods) {
				boolean foundMethod = false;
				methodSignature = singleMethod.split(":");

				methodName = methodSignature[0];
				returnType = methodSignature[1];

				cls = Class.forName(className);
				Method[] methods = cls.getMethods();
				for (Method findMethod : methods) {
					if (methodName.equals(findMethod.getName())) {
						foundMethod = true;

						Parameter parameter[] = findMethod.getParameters();
						if (methodSignature.length == 2 && parameter.length > 0) {// To check if the method has a
																					// argument

							fail(" You have changed the " + "arugument passed to the '" + methodName
									+ "' method in the class " + className + ". Please stick to the "
									+ "skeleton provided");
						} else if (methodSignature.length == 3) {// To check if the method has a argument
							argument = methodSignature[2].split(";");

							if (argument != null) {
								// Parameter parameter[]=findMethod.getParameters();

								if (argument.length == parameter.length) {
									for (int i = 0; i < argument.length; i++) {
										// System.out.println(argument[i].trim()+"------------------"+parameter[i].getParameterizedType().getTypeName().trim());

										if (argument[i].trim()
												.equals(parameter[i].getParameterizedType().getTypeName().trim())) {
											// LOG.info("The argumemnts passed to the method " + methodName + " is
											// valid");
										} else {

											errorFlag = true;
											// LOG.log(Level.SEVERE, " You have changed the " + "arugument passed to the
											// '" + methodName
											// + "' method in the class "+className+". Please stick to the " + "skeleton
											// provided");
											fail(" You have changed the " + "arugument passed to the '" + methodName
													+ "' method in the class " + className + ". Please stick to the "
													+ "skeleton provided");
											break;

										}

									}
								} else {

									errorFlag = true;
									// LOG.log(Level.SEVERE, " You have changed the " + "arugument passed to the '"
									// + methodName
									// + "' method in the class "+className+". Please stick to the " + "skeleton
									// provided");
									fail(" You have changed the " + "arugument passed to the '" + methodName
											+ "' method in the class " + className + ". Please stick to the "
											+ "skeleton provided");

								}

							}
						}

						if (!(findMethod.getReturnType().getName().equals(returnType))) {

							errorFlag = true;
							// LOG.log(Level.SEVERE, " You have changed the " + "return type in '" +
							// methodName
							// + "' method in the class "+className+". Please stick to the " + "skeleton
							// provided");
							fail(" You have changed the " + "return type in '" + methodName + "' method in the class "
									+ className + ". Please stick to the " + "skeleton provided");

						} else {
							// LOG.info("Method signature of " + methodName + " is valid");
						}

					}
				}
				if (!foundMethod) {
					errorFlag = true;
					// LOG.log(Level.SEVERE, " Unable to find the given public method " + methodName
					// + "in the class "+className+". Do not change the " + "given public method
					// name. " + "Verify it with the skeleton");
					fail(" Unable to find the given public method " + methodName + " in the class " + className
							+ ". Do not change the " + "given public method name. " + "Verify it with the skeleton");
				}

			}
			if (!errorFlag) {
				// LOG.info("Method signature is valid");
			}

		} catch (Exception e) {
			e.printStackTrace();
			// LOG.log(Level.SEVERE,
			// " There is an error in validating the " + "method structure. Please manually
			// verify that the "
			// + "Method signature is same as the skeleton before uploading");
			fail("\" There is an error in validating the \" + \"method structure. Please manually verify that the \"\r\n"
					+ "							+ \"Method signature is same as the skeleton before uploading\"");
		}
	}

}
