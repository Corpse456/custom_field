package com.generation_p.hotel_demo.services;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.generation_p.hotel_demo.entity.Hotel;
import com.generation_p.hotel_demo.services.memory.HotelServiceMemory;

public class FacilitiesService {
	protected WebDriver driver;
	private WebDriverWait waitDriver;
	protected static final String BASE_URL = "http://localhost:8080";
	private HotelServiceMemory service = HotelServiceMemory.getInstance();

	public void click() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.get(BASE_URL);
		waitDriver = new WebDriverWait(driver, 30);

		Thread.sleep(5000);
		String gridPath = "//*[@id='HotelGrid']/div[3]/table/tbody/tr";
		waitDriver.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='HotelGrid']/div[3]/table/tbody/tr[1]/td[5]/a")));
		
		List<Hotel> allHotels = service.findAll();

		List<WebElement> rows = driver.findElements(By.xpath(gridPath));
		for (WebElement row : rows) {
			String name = row.findElement(By.xpath("./td[1]")).getText();
			
			@SuppressWarnings ("unused")
            Hotel currentHotel;
			for (Hotel hotel : allHotels) {
				if (hotel.getName().equals(name)) currentHotel = hotel;
			}
			
			String current = driver.getWindowHandle();
			row.findElement(By.tagName("a")).click();

			String newWindowHandle = waitDriver.until(driver -> {
				Set<String> newWindowsSet = driver.getWindowHandles();
				newWindowsSet.remove(current);
				return newWindowsSet.size() > 0 ? newWindowsSet.iterator().next() : null;
			});

			driver.switchTo().window(newWindowHandle);
			Thread.sleep(2000);
			
			List<WebElement> facilities = driver.findElements(By.xpath("//div[@class='important_facility ']"));
			for (WebElement facility : facilities) {
				System.out.println(facility.getText());
			}
			break;
		}

		driver.close();

		driver.quit();
	}
}
