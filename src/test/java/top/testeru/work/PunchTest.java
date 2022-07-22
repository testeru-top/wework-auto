package top.testeru.work;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import top.testeru.user.base.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

/**
 * @program: appium-sample
 * @author: testeru.top
 * @description: 打卡
 * @Version 1.0
 * @create: 2022/7/21 18:35
 */
public class PunchTest extends BaseDaKaTest {


    @Test
    public void test(){
        driver.findElement(AppiumBy.xpath("//*[@text=\"工作台\"]")).click();
        List<String> textList = new ArrayList<>();
        do{
            swipeUp();
            List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
            elements.forEach(webElement -> textList.add(webElement.getText()));
            System.out.println(textList);
        } while(!textList.contains("打卡"));

        WebElement element1 = driver.findElement(AppiumBy.xpath("//*[@text=\"打卡\"]"));
        element1.click();
        webDriverWait.until(webDriver -> webDriver.getPageSource().contains("上下班打卡"));
        do {
            textList.clear();
            List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
            elements.forEach(webElement -> textList.add(webElement.getText()));
        }while(!textList.contains("你已在打卡范围内"));

        driver.findElement(AppiumBy.id("com.tencent.wework:id/bh6")).click();


        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(driver.getPageSource().contains("正常"));
        String text = driver.findElement(AppiumBy.id("com.tencent.wework:id/sr")).getText();
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
