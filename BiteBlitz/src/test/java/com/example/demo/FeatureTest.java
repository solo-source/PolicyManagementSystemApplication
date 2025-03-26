package com.example.demo;

//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import jakarta.persistence.Column;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FeatureTest {
	static boolean flag2;

	@Test
	public void test11ForEntity() throws ClassNotFoundException, IOException {
		if (!checkAnnotationPropertyForclass("com.entities.Restaurant", "Entity"))
			Assert.fail("Check for Entity annotation in Restaurant class");
		if (!checkAnnotationPropertyForclass("com.entities.Orders", "Entity"))
			Assert.fail("Check for Entity annotation in Orders class");
	}

	@Test
	public void test12AutowiredAnnotationInOrderControllerClass() throws ClassNotFoundException, IOException {
		if (!checkAnnotationPropertyForFields("com.controller.OrderController", "Autowired", "orderService"))
			Assert.fail("Autowired annotation not applied for orderService attribute in OrderController class");
	}

	@Test
	public void test13AutowiredAnnotationInRestaurantControllerClass() throws ClassNotFoundException, IOException {
		if (!checkAnnotationPropertyForFields("com.controller.RestaurantController", "Autowired", "restaurantService"))
			Assert.fail("Autowired annotation not applied for restaurantService attribute in RestaurantController class");
	}

	

	

	@Test
	public void test16IdAnnotationInOrdersClass() throws ClassNotFoundException, IOException {
		if (!checkAnnotationPropertyForFields("com.entities.Orders", "Id", "orderId"))
			Assert.fail("Id annotation not applied for the orderId attribute in the Orders class");
	}

	@Test
	public void test17IdAnnotationInRestaurantClass() throws ClassNotFoundException, IOException {
		if (!checkAnnotationPropertyForFields("com.entities.Restaurant", "Id", "restaurantId"))
			Assert.fail("Id annotation not applied for the restaurantId attribute in the Restaurant class");
	}

	@Test
	public void test18MappingInOrdersClass() throws ClassNotFoundException, IOException {
		if (!checkAnnotationPropertyForFields("com.entities.Orders", "ManyToOne", "restaurantObj"))
			Assert.fail("Proper annotation not applied for Restaurant member in Orders class");
	}

	@Test
	public void test19MappingInRestaurantClass() throws ClassNotFoundException, IOException {

		testForMappingAndAttribute("com.entities.Restaurant", "ordersList", "OneToMany", "mappedBy", "restaurantObj");
	}

	@Test
	public void test20JoinColOrdersClass() throws ClassNotFoundException, IOException {

		testForMappingAndAttribute("com.entities.Orders", "restaurantObj", "JoinColumn", "name", "restaurant_id");
	}

	public static boolean checkAnnotationPropertyForFields(String className, String annotationProperty,
			String fieldName) throws IOException, ClassNotFoundException {
		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(className);
		Annotation[] annotations = null;
		boolean flag = false;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				annotations = field.getAnnotations();

				for (Annotation a : annotations) {

					if (a.toString().contains(annotationProperty)) {
						flag = true;
						break;
					}
				}
			}

		}
		return flag;
	}

	public void testForMappingAndAttribute(String className, String fieldName, String annotationProperty,
			String attributeName, String attValue) throws ClassNotFoundException, IOException {
//		if(!checkAnnotationPropertyForclass("com.bean.Account", "Inheritance"))
//			Assert.fail("Check for annotation and properties in Account class is as per the requirement");
		try {

			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(className);
			Annotation[] annotations = null;
			boolean flag = false, flag1 = false, flag2 = false;
			for (Field field : clazz.getDeclaredFields()) {
				// System.out.println(field.getType());
				if (field.getName().toString().equals(fieldName)) {
					flag = true;
					System.out.println(field.getType());
					annotations = field.getAnnotations();
					for (Annotation a : annotations) {
						System.out.println(a);

						if (a.toString().contains(annotationProperty)) {
							flag1 = true;
							for (Method method : a.annotationType().getDeclaredMethods()) {
								Object value = method.invoke(a, (Object[]) null);
								System.out.println(" " + method.getName() + ": " + value.toString());
								if (method.getName().equals(attributeName) && value.toString().contains(attValue)) {

									flag2 = true;
									break;

								}

							}
						}

						if (flag1 == true)
							break;
					}
				}
				if (flag == true) {
					break;
				}
			}
			System.out.println(flag1 + " " + flag2);
// 			Assert.assertTrue(flag1);
			Assert.assertTrue(flag1 && flag2);
		} catch (Exception | Error e) {
			e.printStackTrace();
			Assert.fail("Check if correct annotation and attributes are provided to the field " + fieldName
					+ " in class " + className);
		}

	}

	public static boolean checkAnnotationPropertyForclass(String className, String annotationProperty)
			throws IOException, ClassNotFoundException {

		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(className);
		Annotation[] annotations = null;
		annotations = clazz.getAnnotations();
		boolean flag = false;
		for (Annotation a : annotations) {
			System.out.println(annotationProperty + " " + a);
			if (a.toString().contains(annotationProperty)) {
				flag = true;
				break;
			}
		}
		return flag;

	}
	
	 @Test
	    public void test25ColumnLengthForStringFieldsInOrdersClass() throws ClassNotFoundException, IOException {
	        // Pass the class names as an array
	      
	        String msg1 = checkColumnLengthForStringFields("com.entities.Orders","deliveryAddress",50);
	        String msg2 = checkColumnLengthForStringFields("com.entities.Orders","paymentMethod",25);
	        String msg3 = checkColumnLengthForStringFields("com.entities.Orders","status",25);
	        
	        if(!msg1.equals(" ")){
	            Assert.fail(msg1);
	        } else if(!msg2.equals(" ")){
	            Assert.fail(msg2);
	        } else if(!msg3.equals(" ")){
	            Assert.fail(msg3);
	        }
	    
	       
	       
	    }
	    @Test
	    public void test26ColumnLengthForStringFieldsInRestaurantClass() throws ClassNotFoundException, IOException {
	        // Pass the class names as an array
	      
	    	 String msg1 = checkColumnLengthForStringFields("com.entities.Restaurant","restaurantName",25);
		        String msg2 = checkColumnLengthForStringFields("com.entities.Restaurant","cuisine",25);
		        String msg3 = checkColumnLengthForStringFields("com.entities.Restaurant","location",25);
		        
		        if(!msg1.equals(" ")){
		            Assert.fail(msg1);
		        } else if(!msg2.equals(" ")){
		            Assert.fail(msg2);
		        } else if(!msg3.equals(" ")){
		            Assert.fail(msg3);
		        }
		    
	    }
	    
	    private String checkColumnLengthForStringFields(String className, String fieldName, int expectedLength) throws ClassNotFoundException, IOException {
	        String msg="In class "+className+" for the Field ";
	            Field field = null;
	            try {
	                @SuppressWarnings("rawtypes")
	                Class clazz = Class.forName(className);
	                Field[] fields = clazz.getDeclaredFields();

	                for (Field f : fields) {
	                	
	                    if (f.getType().equals(String.class) && f.getName().equals(fieldName))  {
	                        field = f;
	                        Column column = field.getAnnotation(Column.class);
	                        if (column == null || column.length() != expectedLength) {
	                           msg=msg+ field.getName()+"  ";
	                           //    "' does not have Column annotation with length set to " + expectedLength;
	                        }
	                    }
	                }
	            } catch (Exception  e) {
	                e.printStackTrace();
	                return ("Check if correct annotation is provided for String fields to set the column size to required length in  " + className + " class");
	               
	            }
	            if(msg.equals("In class "+className+" for the Field "))
	              return " ";
	            else 
	              return msg+"check if you have provided Column annotation with required length";
	            
	        
	    }

	      @Test
	  	public void test21GetterAnnotationInRestaurant() {
	  	    try {
	  	        boolean flag = false;
	  	        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/com/entities/Restaurant.java")));
	  	        String str = "";
	  	        int f1 = 0, f2 = 0, f3 = 0;

	  	        while ((str = br.readLine()) != null) {
	  	            if (str.contains("@Getter")) {
	  	                f1 = 1;
	  	            } else if (str.contains("@Setter")) {
	  	                f2 = 1;
	  	            } else if (str.contains("@Data")) {
	  	                f3 = 1;
	  	            } else if (str.replace(" ", "").contains("publicclassRestaurant")) {
	  	                break;
	  	            }
	  	        }

	  	        assertTrue((f1 == 1 && f2 == 1) || f3 == 1);
	  	    } catch (Error | Exception e) {
	  	        e.printStackTrace();
	  	        fail("Check for the usage of Lombok to generate getter/setter in Restaurant class");
	  	    }
	  	}

	  	
	  	@Test
	  	public void test22ConstructorAnnotationInRestaurant() {
	  	    try {
	  	        boolean flag = false;
	  	        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/com/entities/Restaurant.java")));
	  	        String str = "";

	  	        while ((str = br.readLine()) != null) {
	  	            if (str.contains("@AllArgsConstructor")) {
	  	                flag = true;
	  	                break;
	  	            } else if (str.replace(" ", "").contains("publicclassRestaurant")) {
	  	                break;
	  	            }
	  	        }

	  	        assertTrue(flag);
	  	    } catch (Error | Exception e) {
	  	        e.printStackTrace();
	  	        fail("Check the availability of appropriate Lombok annotation to generate all argument constructor in Restaurant class");
	  	    }
	  	}
	  	
	  	@Test
		public void test23ConstructorAnnotationInOrders() {
		    try {
		        boolean flag = false;
		        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/com/entities/Orders.java")));
		        String str = "";

		        while ((str = br.readLine()) != null) {
		            if (str.contains("@AllArgsConstructor")) {
		                flag = true;
		                break;
		            } else if (str.replace(" ", "").contains("publicclassOrders")) {
		                break;
		            }
		        }

		        assertTrue(flag);
		    } catch (Error | Exception e) {
		        e.printStackTrace();
		        fail("Check the availability of appropriate Lombok annotation to generate all argument constructor in Orders class");
		    }
		}


		@Test
		public void test24GetterAnnotationInOrders() {
		    try {
		        boolean flag = false;
		        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/com/entities/Orders.java")));
		        String str = "";
		        int f1 = 0, f2 = 0, f3 = 0;

		        while ((str = br.readLine()) != null) {
		            if (str.contains("@Getter")) {
		                f1 = 1;
		            } else if (str.contains("@Setter")) {
		                f2 = 1;
		            } else if (str.contains("@Data")) {
		                f3 = 1;
		            } else if (str.replace(" ", "").contains("publicclassOrders")) {
		                break;
		            }
		        }

		        assertTrue((f1 == 1 && f2 == 1) || f3 == 1);
		    } catch (Error | Exception e) {
		        e.printStackTrace();
		        fail("Check for the usage of Lombok to generate getter/setter in Orders class");
		    }
		}


}
	  
	
	
	