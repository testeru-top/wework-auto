package top.testeru.user;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import top.testeru.user.base.BaseTest;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberTest extends BaseTest {

    @BeforeEach
    void beforeEach(){
        //2、点击通讯录
        driver.findElement(AppiumBy.xpath("//*[@text=\"通讯录\"]")).click();
    }

    @Order(1)
    @Test
    public void add(){
//3、点击添加成员
        List<WebElement> elements = driver.findElements(AppiumBy.id("com.tencent.wework:id/f_x"));

        elements.get(elements.size()-1).click();
//4、手动输入添加
        driver.findElement(AppiumBy.xpath("//*[@text=\"手动输入添加\"]")).click();

        //输入姓名
        driver.findElement(AppiumBy.id("com.tencent.wework:id/bwp")).sendKeys(name);
        //输入手机号
        driver.findElement(AppiumBy.id("com.tencent.wework:id/hyw")).sendKeys(zh_phone);
        //点击保存按钮
        driver.findElement(AppiumBy.id("com.tencent.wework:id/aw3")).click();

        //添加toast判断
        WebElement element2 = driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.Toast"));
        String text = element2.getText();
        System.out.println(text);


        //
//        driver.findElement(AppiumBy.xpath("//*[@text=\"添加成员\"]/../../../preceding-sibling::*")).click();
        //点击返回com.tencent.wework:id/kz6
        WebElement element1 = driver.findElement(AppiumBy.id("com.tencent.wework:id/kz6"));
        element1.click();

        webDriverWait.until(webDriver -> webDriver.getPageSource().contains("添加客户"));

        //搜索查找
        driver.findElement(AppiumBy.id("com.tencent.wework:id/l00")).click();
        WebElement element = driver.findElement(AppiumBy.xpath("//*[@text=\"搜索\"]"));
        element.clear();
        element.sendKeys(name);



        WebElement memberEle = driver.findElement(AppiumBy.xpath("//*[@text=\"联系人\"]/../following-sibling::*//*[@class=\"android.widget.TextView\"]"));
        String memberName = memberEle.getText();
        System.out.println(memberName);
        //点击联系人下的第一个搜索结果，进入到个人信息页面
        memberEle.click();
        webDriverWait.until(webDriver -> webDriver.getPageSource().contains("手机"));
        //获取个人到手机号
        String phone = driver.findElement(AppiumBy.id("com.tencent.wework:id/bxg")).getText();

        //点击返回
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/kz6")).click();
        swipeRight();

        assertAll(
                ()-> assertThat(name,is(equalTo(memberName))),
                ()->  assertThat(zh_phone,is(equalTo(phone))),
                //添加toast断言
                ()->  assertThat(text,is(equalTo("添加成功")))

        );




    }
    @Test
    @Order(3)
    public void delete(){
//搜索进入到个人信息页面
        driver.findElement(AppiumBy.id("com.tencent.wework:id/l00")).click();
        WebElement element = driver.findElement(AppiumBy.xpath("//*[@text=\"搜索\"]"));
        element.clear();
        element.sendKeys(name);
        WebElement memberEle = driver.findElement(AppiumBy.xpath("//*[@text=\"联系人\"]/../following-sibling::*//*[@class=\"android.widget.TextView\"]"));
        memberEle.click();


        //com.tencent.wework:id/kzp
        driver.findElement(AppiumBy.id("com.tencent.wework:id/kzp")).click();

        //点击 编辑成员
        driver.findElement(AppiumBy.xpath("//*[@text=\"编辑成员\"]")).click();


//3、w3c滑动到底部查找到元素添加成员
        List<String> textList = new ArrayList<>();
        do{
            swipeUp();
            List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
            elements.forEach(webElement -> textList.add(webElement.getText()));
            System.out.println(textList);
        } while(!textList.contains("删除成员"));


//        try {
//            sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        webDriverWait.until(webDriver -> webDriver.getPageSource().contains("删除成员"));
        WebElement element1 = driver.findElement(AppiumBy.xpath("//*[@text=\"删除成员\"]"));
        element1.click();

        driver.findElement(AppiumBy.xpath("//*[@text=\"确定\"]")).click();


        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //返回
         swipeRight();
        //再次搜索
        driver.findElement(AppiumBy.id("com.tencent.wework:id/l00")).click();
        element = driver.findElement(AppiumBy.xpath("//*[@text=\"搜索\"]"));
        element.clear();
        element.sendKeys(name);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        List<String> endTextList = new ArrayList<>();
        List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
        elements.forEach(webElement -> endTextList.add(webElement.getText()));
        System.out.println(endTextList);

        assertAll(
                () -> assertTrue(driver.getPageSource().contains("无搜索结果")),
                () -> assertTrue(endTextList.contains("无搜索结果"))
        );




    }
    @AfterEach
    public void afterEach(){
        //点击返回
//        driver.findElement(AppiumBy.id("com.tencent.wework:id/kz6")).click();
        swipeRight();

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
    private void swipeRight() {
        Dimension dimension = driver.manage().window().getSize();
        Point start = new Point((int)(dimension.width*0.0), (int)(dimension.height*0.5));
        Point end = new Point((int)(dimension.width*0.9), (int)(dimension.height*0.5));

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
