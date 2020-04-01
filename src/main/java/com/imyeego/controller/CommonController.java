package com.imyeego.controller;

import com.imyeego.pojo.HearBean;
import com.imyeego.pojo.Process;
import com.imyeego.pojo.ProcessInfo;
import com.imyeego.websocket.SpringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bjkw")
public class CommonController {


    @RequestMapping(value = "/dataApi/heartBeat", method = RequestMethod.POST)
    public HearBean heartBeat(@RequestParam("harddata") String harddata, @RequestParam("orgcode") String orgcode,
                              @RequestParam("taskcode") String taskcode, @RequestParam("terminaltime") String terminaltime) {
        System.out.println("thread name " + Thread.currentThread().getName());
        HearBean bean = new HearBean();
        bean.setMessage("ok");
        bean.setState("1");
        bean.setServertime(System.currentTimeMillis());
        return bean;
    }

    @RequestMapping(value = "/mobileApi/getProcessinfo", method = RequestMethod.POST)
    public List<Process> getProcessInfo(@RequestParam("taskcode") String taskcode) {
        List<Process> list = new ArrayList<>();
        for (int j = 1; j < 6; j++) {
            Process process = new Process();
            process.setRoomsession(String.valueOf(j));
            List<ProcessInfo> infos = new ArrayList<>();
            for (int i = -3; i < 5; i++) {
                if (i == -1) continue;
                ProcessInfo info = new ProcessInfo();
                info.setProcessname("进程" + Math.abs(i + 1));
                info.setSortnum(String.valueOf(i));
                info.setProcesstimedata(String.format("%d0", i + 1));
                infos.add(info);
            }
            process.setSessionData(infos);
            list.add(process);
        }

        return list;
    }

}
