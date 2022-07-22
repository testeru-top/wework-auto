package top.testeru.user;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import top.testeru.util.FakerUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @program: appium-sample
 * @author: testeru.top
 * @description:
 * @Version 1.0
 * @create: 2022/7/22 11:08
 */
public class BaseMemeber {
    public static AndroidDriver driver;
    public static String name;
    public static String zh_phone;
    public static WebDriverWait webDriverWait;
    @BeforeAll
    public static void beforeAll(){
        name = FakerUtil.get_name();
        zh_phone = FakerUtil.get_zh_phone();

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //平台名称 安卓系统就是Android 苹果手机就是iOS
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        //使用的driver uiautomator2
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        //设备的系统版本
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0.0");
        //启动的app的包名
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.tencent.wework");
        //启动的app的页面
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".launch.LaunchSplashActivity");
        //设备名称
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "盖盖");
        //设备的UDID；adb devices -l 获取，多设备的时候要指定，若不指定默认选择列表的第一个设备
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "8c5f5f92");
        //app不重置
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        //运行失败的时候打印page source到appium-log
        desiredCapabilities.setCapability(MobileCapabilityType.PRINT_PAGE_SOURCE_ON_FIND_FAILURE, true);
        //在假设客户端退出并结束会话之前，Appium 将等待来自客户端的新命令多长时间（以秒为单位） http请求等待响应最长5分钟
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300000);
        //默认权限通过
        desiredCapabilities.setCapability(SupportsAutoGrantPermissionsOption.AUTO_GRANT_PERMISSIONS_OPTION, true);

//1.启动微信app--创建driver
        try {
            driver = new AndroidDriver(
                    new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        //隐式等待 设置一次，全局生效
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(2));
//2、点击通讯录，跳转到通讯录页面
//        driver.findElement(AppiumBy.xpath("//*[@text=\"通讯录\"]")).click();
        driver.findElement(AppiumBy.cssSelector("[text=\"通讯录\"]")).click();

    }

    @Test
    public void add(){
//3、点击添加成员，跳转到添加成员页面
//        driver.findElement(AppiumBy.xpath("//*[@text=\"添加成员\"]")).click();
        driver.findElement(AppiumBy.cssSelector("[text=\"添加成员\"]")).click();

        //List<WebElement> elements = driver.findElements(AppiumBy.id("com.tencent.wework:id/f_x"));
        //elements.get(elements.size()-1).click();
//4、点击手动输入添加，跳转到输入成员信息页面
//        driver.findElement(AppiumBy.xpath("//*[@text=\"手动输入添加\"]")).click();
        driver.findElement(AppiumBy.cssSelector("[text=\"手动输入添加\"]")).click();

        //输入姓名
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/bwp")).sendKeys(name);
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/bwp")).sendKeys(name);

        //输入手机号
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/hyw")).sendKeys(zh_phone);
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/hyw")).sendKeys(zh_phone);

//5、点击保存，toast显示添加成功，跳转到添加成员页面
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/aw3")).click();
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/aw3")).click();

//6、返回到通讯录页面 添加成员页面返回com.tencent.wework:id/kz6
//        driver.findElement(AppiumBy.xpath("//*[@text=\"添加成员\"]/../../../preceding-sibling::*")).click();
//        WebElement element1 = driver.findElement(AppiumBy.id("com.tencent.wework:id/kz6"));
        WebElement element1 = driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/kz6"));
        element1.click();

        webDriverWait.until(webDriver -> webDriver.getPageSource().contains("添加客户"));

        //搜索查找
//7、通讯录页面点击搜索按钮
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/l00")).click();
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/l00")).click();

//8、搜索框输入姓名
//        WebElement element = driver.findElement(AppiumBy.xpath("//*[@text=\"搜索\"]"));
        WebElement element = driver.findElement(AppiumBy.cssSelector("[text=\"搜索\"]"));

        element.clear();
        element.sendKeys(name);


//9、点击搜索结果下的联系人下的第一个结果，跳转到个人信息页面
        WebElement memberEle = driver.findElement(AppiumBy.xpath("//*[@text=\"联系人\"]/../following-sibling::*//*[@class=\"android.widget.TextView\"]"));
//        WebElement memberEle = driver.findElement(AppiumBy.cssSelector("[text=\"联系人\"]/../following-sibling::*//*[class=\"android.widget.TextView\"]"));

        //点击联系人下的第一个搜索结果，进入到个人信息页面
        memberEle.click();
        webDriverWait.until(webDriver -> webDriver.getPageSource().contains("手机"));
// 10、获取姓名、手机号
        //获取个人姓名
//        String memberName = driver.findElement(AppiumBy.id("com.tencent.wework:id/kbc")).getText();
        String memberName = driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/kbc")).getText();

        //获取个人到手机号
//        String phone = driver.findElement(AppiumBy.id("com.tencent.wework:id/bxg")).getText();
        String phone = driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/bxg")).getText();

//11、返回到搜索结果页面
        //点击返回 个人信息页面的返回 com.tencent.wework:id/kz6
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/kz6")).click();
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/kz6")).click();

//12、返回到通讯录页面
        //点击返回 搜索页面的返回 com.tencent.wework:id/kz6


//        driver.findElement(AppiumBy.id("com.tencent.wework:id/j_s")).click();
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/j_s")).click();
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/kz6")).click();
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/kz6")).click();
//13、验证姓名、手机号是否一致
        assertAll(
                ()-> assertThat(name,is(equalTo(memberName))),
                ()->  assertThat(zh_phone,is(equalTo(phone)))
        );




    }
}
