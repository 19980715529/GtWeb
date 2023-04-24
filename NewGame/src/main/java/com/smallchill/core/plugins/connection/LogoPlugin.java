package com.smallchill.core.plugins.connection;

import com.smallchill.core.plugins.IPlugin;

public class LogoPlugin implements IPlugin {

	public void start() {
        printLogo();
    }

	public void stop() {
        printLogo();
	}

    public void printLogo() {
        StringBuilder sb = new StringBuilder();
        sb.append(" _____               _                 ______  _             _\n");
        sb.append("/  ___|             (_)                | ___ \\| |           | |\n");
        sb.append("\\ `--.  _ __   _ __  _  _ __    __ _   | |_/ /| |  __ _   __| |  ___\n");
        sb.append(" `--. \\| '_ \\ | '__|| || '_ \\  / _` |  | ___ \\| | / _` | / _` | / _ \\\n");
        sb.append("/\\__/ /| |_) || |   | || | | || (_| |  | |_/ /| || (_| || (_| ||  __/\n");
        sb.append("\\____/ | .__/ |_|   |_||_| |_| \\__, |  \\____/ |_| \\__,_| \\__,_| \\___|\n");
        sb.append("       | |                      __/ |\n");
        sb.append("       |_|                     |___/");
        System.out.println(sb.toString());
    }

}
