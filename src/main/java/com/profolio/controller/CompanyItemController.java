package com.profolio.controller;

import com.profolio.dto.ContactDto;
import com.profolio.dto.ItemFormDto;
import com.profolio.dto.ItemSearchDto;
import com.profolio.dto.ScrapDto;
import com.profolio.entity.Item;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CompanyItemController {

    private final ItemService itemService;


    @GetMapping(value ={ "/company/portfolios/share","/company/portfolios/share/{page}","/company"})
    public String companyItemShare(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Page<Item> items = itemService.getShareItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "company/itemShare";
    }
    @GetMapping(value = {"/company/share/{itemId}"})
    public String companyItemSharePage(@PathVariable("itemId") Long itemId, ContactDto contactDto, Model model){
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
            model.addAttribute("contactDto",contactDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "company/itemShare";
        }

        return "company/itemLikeForm";
    }
    @PostMapping(value = {"/company/share/pdfOpen/{itemId}"})
    public String companyItemSharePdfOpenPage(@PathVariable("itemId") Long itemId,Model model){


        String url = itemService.findByURL(itemId);
        url=url.substring(2);

        model.addAttribute("url",url);
        return "item/pdf/pdfView";
    }

    @GetMapping(value = "/company/{itemId}")
    public String companyItemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "company/itemDtl";
    }
    private final ScrapService scrapService;

    @GetMapping(value="/company/scrap")
    public String readScrap(Model model,@PathVariable("page") Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
//        Page<ScrapDto> scrapDto = scrapService.readScrap(username,pageable);
        List<ScrapDto> scrapDto = scrapService.readScrap(username,pageable);
        model.addAttribute("scrapDto", scrapDto);
        System.out.println(scrapDto);
        return "company/scrapMng";
    }

    @PostMapping(value = "/company/scrap/new")
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
            return "redirect:/company/scrap";

        } catch (Exception e){
            model.addAttribute("errorMessage", "자료 등록 중 에러가 발생하였습니다.");
            return "company/scrapMng";
        }
//        return "/members/share/"+itemFormDto.getId();


    }
//    //https://ayoteralab.tistory.com/entry/Spring-Boot-13-REST-API-3-PUTDELETE-method
//    @PutMapping(value="/{code}")
//    public int updateCommu(@PathVariable("code") int code,@RequestBody CommuDto commuDto){
//        int result = commuService.updateCommu(code,commuDto);
//        return result;
//    }

    @PostMapping(value = "/company/scrap/{code}")
    public void deleteScrap(@PathVariable("code") int code,Model model){

    }

    @PostMapping(value = {"/company/scrap/delete","/members/scrap/delete"})
    public String companyContactSend(@RequestParam(value="deleteList[]") List<Long> deleteList, HttpServletRequest request){
        itemService.deleteScrap(deleteList);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
