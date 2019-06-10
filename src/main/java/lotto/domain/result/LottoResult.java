package lotto.domain.result;

import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.Lottos;
import lotto.domain.purchase.PurchaseAmount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class LottoResult {
    private static final int INIT_VALUE = 0;
    private static final int PERCENTAGE = 100;

    private static Map<LottoRank, Integer> map = new LinkedHashMap<>();
    private final Winning winning;
    private final Lottos lottos;

    private LottoResult(Winning winning, Lottos lottos) {
        this.winning = winning;
        this.lottos = lottos;
        map = matchWinningLotto();
    }

    public static LottoResult of(Winning winning, Lottos lottos) {
        return new LottoResult(winning, lottos);
    }

    private Map<LottoRank, Integer> matchWinningLotto() {
        Map<LottoRank, Integer> map = new LinkedHashMap<>();
        init(map);
        addLottoResult(map);
        return map;
    }

    private void init(Map<LottoRank, Integer> map) {
        List<LottoRank> lottoRanks = Arrays.asList(LottoRank.values());
        Collections.reverse(lottoRanks);
        for (LottoRank lottoRank : lottoRanks) {
            map.put(lottoRank, INIT_VALUE);
        }
    }

    private void addLottoResult(Map<LottoRank, Integer> map) {
        for (Lotto lotto : lottos.getLottos()) {
            LottoRank lottoRank = winning.checkWinner(lotto);
            map.replace(lottoRank, map.get(lottoRank) + 1);
        }
    }

    public BigDecimal yield() {
        BigDecimal purchaseAmount = new BigDecimal(
                PurchaseAmount.LOTTO_PRICE * map.values().stream()
                .reduce(INIT_VALUE, Integer::sum));
        BigDecimal result = new BigDecimal(map.keySet().stream()
                .mapToInt(x -> x.getMoney() * map.get(x))
                .sum());

        return result.divide(purchaseAmount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(PERCENTAGE));
    }

    public Map<LottoRank, Integer> getMap() {
        return map;
    }
}
