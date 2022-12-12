package com.profolio.service;

import com.profolio.controller.PdfToJpgService;
import com.profolio.dto.ItemFormDto;
import com.profolio.entity.Item;
import com.profolio.entity.ItemImg;
import com.profolio.repository.ItemImgRepository;
import com.profolio.repository.ItemRepository;
import com.profolio.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.profolio.dto.ItemImgDto;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import com.profolio.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.profolio.dto.MainItemDto;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    private final ScrapRepository scrapRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }


        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Item> getShareItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getShareItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Item> getMyItemPage(ItemSearchDto itemSearchDto, Pageable pageable,String createdBy){
        return itemRepository.getMyItemPage(itemSearchDto, pageable,createdBy);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }


    public String findByURL(Long id) {
        return itemImgRepository.findFirstByItemId(id).getImgUrl();
    }

    public void deleteItem(List<Long> deleteList) {
        for (Long number :
                deleteList) {
            scrapRepository.deleteByItemId(number);
            itemImgRepository.deleteByItemId(number);
            itemRepository.deleteById(number);
        }
    }

    public void deleteScrap(List<Long> deleteList) {
        for (Long id:
                deleteList) {
            scrapRepository.deleteById(id);
        }

    }
}