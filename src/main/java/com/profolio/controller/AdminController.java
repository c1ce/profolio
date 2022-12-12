package com.profolio.controller;

import com.profolio.dto.ItemSearchDto;
import com.profolio.entity.Item;
import com.profolio.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ItemService itemService;
    @GetMapping(value ={ "/admin/share"})
    public String adminSharePage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Page<Item> items = itemService.getShareItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "admin/itemShare";
    }

    @PostMapping(value ={ "/admin/share/delete"})
    public String adminShareDelete(@RequestParam(value="deleteList[]") List<Long> deleteList){

        itemService.deleteItem(deleteList);
        return "redirect:/admin/share";
    }
}
