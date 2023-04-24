package com.smallchill.game.player.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Withdraw")
public class PlayWithdrawController {

    private static final Logger logger = LoggerFactory.getLogger(PlayWithdrawController.class);
    private static final String BASE_PATH = "/gameplayer/";

    @RequestMapping("/page")
    public void aaa(){

    }

}
