package com.tw.jiracalc.controller;

import com.tw.jiracalc.config.MapConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@EnableConfigurationProperties
public class WelcomeController {
    @Value("${welcome.message}")
    private String message;

    @Value("${server.port}")
    private int port;

    @Autowired
    private MapConfig mapConfig;

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/")
    public String main(Model model) throws UnknownHostException {
        model.addAttribute("message", message);
        model.addAttribute("tasks", tasks);

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());
        System.out.println("Port:- " + port);
        model.addAttribute("ip", inetAddress.getHostAddress());
        model.addAttribute("host", inetAddress.getHostName());

        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);

        return "welcome"; //view
    }
}
