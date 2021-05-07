package search;

import org.testng.annotations.Test;

import com.datadriven.framework.base.BaseUI;

public class searchTest extends BaseUI{
	@Test
	public void chrome() throws Exception {
		openBrowser("chrome");
		openURL("websiteURL");
		enterText("searchTextBox_id","outdoor toys");
		elementClick("searchBtn_id");
		verifyTitle();
		getLinks();
		portable("searchText","productId");
		screenshot();
		closeBrowser();
	}
	
	@Test(dependsOnMethods="chrome")
	public void fireFox() throws Exception {
		openBrowser("Firefox");
		openURL("websiteURL");
		enterText("searchTextBox_id","outdoor toys");
		elementClick("searchBtn_id");
		verifyTitle();
		getLinks();
		portable("searchText","productId");
		screenshot();
		closeBrowser();
	}
	
}
