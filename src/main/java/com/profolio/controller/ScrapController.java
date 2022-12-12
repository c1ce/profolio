package com.profolio.controller;

import com.profolio.dto.ItemFormDto;
import com.profolio.dto.ScrapDto;
import com.profolio.service.ItemService;
import com.profolio.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;
    private final ItemService itemService;

    @GetMapping(value="/members/scrap")
    public String readScrap(Model model,@PathVariable("page") Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
//        Page<ScrapDto> scrapDto = scrapService.readScrap(username,pageable);
        List<ScrapDto> scrapDto = scrapService.readScrap(username,pageable);
        model.addAttribute("scrapDto", scrapDto);
        System.out.println(scrapDto);
        return "scrap/scrapMng";
    }

    @PostMapping(value = "/members/scrap/new")
    public String createScrap(ItemFormDto itemFormDto, Model model, RedirectAttributes re){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        ScrapDto scrapDto = new ScrapDto();
        scrapDto.setScrapUserId(username);
        scrapDto.setItemId(itemFormDto.getId());
        scrapDto.setTitle(itemFormDto.getItemNm());
        System.out.println(itemFormDto.getId());
        String url = itemService.findByURL(itemFormDto.getId());

        scrapDto.setScrapUrl(url);

        try {
            if(!scrapService.createScrap(scrapDto)){
                re.addFlashAttribute("errorMessage", "이미 관심 포트폴리오에 등록하셨습니다.");
            }
            return "redirect:/members/scrap";

        } catch (Exception e){
            model.addAttribute("errorMessage", "자료 등록 중 에러가 발생하였습니다.");
            return "scrap/scrapMng";
        }
//        return "/members/share/"+itemFormDto.getId();


    }
//    //https://ayoteralab.tistory.com/entry/Spring-Boot-13-REST-API-3-PUTDELETE-method
//    @PutMapping(value="/{code}")
//    public int updateCommu(@PathVariable("code") int code,@RequestBody CommuDto commuDto){
//        int result = commuService.updateCommu(code,commuDto);
//        return result;
//    }

    @PostMapping(value = "/members/scrap/{code}")
    public void deleteScrap(@PathVariable("code") int code,Model model){

    }

}