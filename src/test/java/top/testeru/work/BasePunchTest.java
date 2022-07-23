package top.testeru.work;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.ios.options.wda.SupportsWaitForIdleTimeoutOption;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import top.testeru.util.FakerUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

/**
 * @program: wework-auto
 * @author: testeru.top
 * @description:
 * @Version 1.0
 * @create: 2022/7/23 17:19
 */
public class BasePunchTest {
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

        desiredCapabilities.setCapability(SupportsWaitForIdleTimeoutOption.WAIT_FOR_IDLE_TIMEOUT_OPTION, 0);

//        1.启动app--创建driver
        try {
            driver = new AndroidDriver(
                    new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        //隐式等待 设置一次，全局生效
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(2));
    }

    @Test
    public void test(){
//        driver.findElement(AppiumBy.xpath("//*[@text=\"工作台\"]")).click();
        driver.findElement(AppiumBy.cssSelector("[text=\"工作台\"]")).click();

        List<String> textList = new ArrayList<>();
        do{
            swipeUp();
            List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
            elements.forEach(webElement -> textList.add(webElement.getText()));
            System.out.println(textList);
        } while(!textList.contains("打卡"));

//        WebElement element1 = driver.findElement(AppiumBy.xpath("//*[@text=\"打卡\"]"));
        WebElement element1 = driver.findElement(AppiumBy.cssSelector("[text=\"打卡\"]"));

        element1.click();
        webDriverWait.until(webDriver -> webDriver.getPageSource().contains("上下班打卡"));
        do {
            textList.clear();
            List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
            elements.forEach(webElement -> textList.add(webElement.getText()));
        }while(!textList.contains("你已在打卡范围内"));

//        driver.findElement(AppiumBy.id("com.tencent.wework:id/bh6")).click();
        driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/bh6")).click();



        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(driver.getPageSource().contains("正常"));
//        String text = driver.findElement(AppiumBy.id("com.tencent.wework:id/sr")).getText();
        String text = driver.findElement(AppiumBy.cssSelector("#com\\.tencent\\.wework\\:id\\/sr")).getText();

        assertThat(text,is(containsString("·正常")));


    }
    private void swipeUp() {
        Dimension dimension = driver.manage().window().getSize();
        Point start = new Point((int)(dimension.width*0.5), (int)(dimension.height*0.9));
        Point end = new Point((int)(dimension.width*0.5), (int)(dimension.height*0.2));

        /**
         * PointerInput：指针输入源是与指针类型输入设备相关联 的输入源
         * Kind：指点设备的类型。这可以是“mouse”「鼠标」、“pen”「笔」或“touch”「手指」
         * name：指点设备的名字
         */
        PointerInput FINGER = new PointerInput(TOUCH, "finger");

        /** 一系列动作pointerDown，然后是pointerMove，然后是 pointerUp
         * createPointerMove：指针应移动到的屏幕位置，无论是处于活动（按下）还是非活动状态。
         */
        //FINGER 设备; 1 初始长度
        Sequence swipe = new Sequence(FINGER, 1)
                .addAction(FINGER.createPointerMove(ofMillis(0), viewport(), start.getX(), start.getY()))
                .addAction(FINGER.createPointerDown(LEFT.asArg()))
                .addAction(FINGER.createPointerMove(ofMillis(1000), viewport(), end.getX(), end.getY()))
                .addAction(FINGER.createPointerUp(LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
