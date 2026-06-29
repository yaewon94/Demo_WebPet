package com.example.demo_webPet.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
abstract public class BoardController {

    @ModelAttribute("add_url")
    public String addUrl() {
        return getAddUrl();
    }

    @ModelAttribute("list_url")
    public String listUrl() {
        return getListUrl();
    }

    @ModelAttribute("detail_url")
    public String detailUrl() {
        return getDetailUrl();
    }

    @ModelAttribute("modify_url")
    public String modifyUrl() {
        return getModifyUrl();
    }

    @ModelAttribute("delete_url")
    public String deleteUrl() {
        return getDeleteUrl();
    }

    protected abstract String getAddUrl();
    protected abstract String getListUrl();
    protected abstract String getDetailUrl();
    protected abstract String getModifyUrl();
    protected abstract String getDeleteUrl();
}
