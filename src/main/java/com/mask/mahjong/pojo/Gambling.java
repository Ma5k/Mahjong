package com.mask.mahjong.pojo;

import com.mask.mahjong.utils.CardComparator;
import com.mask.mahjong.utils.CardUtil;
import com.mask.mahjong.utils.GamblingUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 牌局
 * @author Mask
 */
@Data
@Slf4j
public class Gambling implements Serializable {

    /**
     * 牌局ID
     */
    private String gamblingID;

    /**
     * 牌池
     */
    private List<Card> cardPool;

    public Gambling(){
        this.gamblingID = UUID.randomUUID().toString();
        this.cardPool = creatCardPool();
    }

    /**
     * 创建牌池（洗牌新开一局）
     * @return
     */
    public List<Card> creatCardPool() {
        if(this.cardPool != null) {
            return this.cardPool;
        }
        List<Card> cards = new ArrayList<Card>(108);
        List<Card> cardPool = new ArrayList<Card>(108);
        for(int i = 0;i < 3;i++) {
            for(int j = 1;j <= 9;j++) {
                int cardNum = i * 10 + j;
                for(int k = 1;k <= 4;k++) {
                    Card card = new Card();
                    card.setCardID(CardUtil.cardCode(cardNum) + "_" + k);
                    card.setName(CardUtil.cardName(cardNum));
                    card.setNumber(cardNum);
                    card.setSuitNum(i);
                    card.setSuit(CardUtil.cardSuit(cardNum, false));
                    card.setPoint(cardNum % 10);
                    cards.add(card);
                }
            }
        }

        for(int i = 0;i < 108;i++) {
            Random random = new Random();
            int randomIndex = random.nextInt(cards.size());
            cardPool.add(cards.get(randomIndex));
            cards.remove(randomIndex);
        }
        return cardPool;
    }
}
