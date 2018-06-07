package com.generation_p.hotel_demo.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.generation_p.hotel_demo.entity.Hotel;

public class FacilitiesService {
	protected WebDriver driver;
	private WebDriverWait waitDriver;
	protected static final String BASE_URL = "http://localhost:8080";
	private HotelService service = ServiceProvider.getHotelService();

	public void click() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.get(BASE_URL);
		waitDriver = new WebDriverWait(driver, 5);
		
		String mainWindowHandle = driver.getWindowHandle();
		List<Hotel> allHotels = service.findAll();
		
		String rowPath = "//*[@id='HotelGrid']/div[3]/table/tbody/tr";
		List<WebElement> visibleRows = waitDriver.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowPath)));
		
		Actions action = new Actions(driver); 
		for (int i = 0; i < allHotels.size(); i++) {
		    int currentRowNumber = i == allHotels.size() - 1 ? visibleRows.size() : i >= visibleRows.size() - 1 ? visibleRows.size() - 1 : i + 1;
		    
            WebElement currentRow = driver.findElement(By.xpath(rowPath + "[" + currentRowNumber + "]"));
			String name = currentRow.findElement(By.xpath("./td[1]")).getText();
			
			if (!allHotels.get(i).getName().equals(name)) {
			    i--;
			    continue;
			}
			
			WebElement link = currentRow.findElement(By.tagName("a"));
			action.moveToElement(link).click();
			action.build().perform(); 
			
			String newWindowHandle = waitDriver.until(driver -> {
				Set<String> newWindowsSet = driver.getWindowHandles();
				newWindowsSet.remove(mainWindowHandle);
				return newWindowsSet.size() > 0 ? newWindowsSet.iterator().next() : null;
			});
			driver.switchTo().window(newWindowHandle);
			
            List<WebElement> facilities = driver.findElements(By.xpath("//div[@id='hotel_main_content']/div/div/div/div[@class='important_facility ']"));
            if (facilities.isEmpty()) {
                returnToMain(mainWindowHandle);
                continue;
            }
            
            facilities.sort((o1, o2) -> o1.getText().compareTo(o2.getText()));
            
            StringBuilder newHotelFacilities = new StringBuilder();
            Iterator<WebElement> iterator = facilities.iterator();
            while (iterator.hasNext()) {
                newHotelFacilities.append(iterator.next().getText());
                if (iterator.hasNext()) newHotelFacilities.append(",");
            }
            
            Hotel currentHotel = allHotels.get(i);
            currentHotel.setFacilities(newHotelFacilities.toString());
            service.save(currentHotel);
			
			returnToMain(mainWindowHandle);
		}

		driver.quit();
	}

    private void returnToMain (String mainWindowHandle) {
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
}
