import java.util.List;

import domain.Lotto;
import domain.LottoCount;
import domain.LottoFactory;
import domain.LottoNumber;
import domain.LottoGameMoney;
import domain.Lottos;
import domain.RandomLottoNumbersGenerator;
import domain.WinningLotto;
import domain.WinningStatistics;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        final LottoGameMoney purchaseMoney = new LottoGameMoney(InputView.getPurchaseAmount());
        Lottos lottos = createLottos(purchaseMoney);
        final WinningLotto winningLotto = createWinningLotto();

        final WinningStatistics winningStatistics = lottos.match(purchaseMoney, winningLotto);
        OutputView.showWinningStatistics(winningStatistics.values());
        OutputView.showProfitRate(winningStatistics.calculateProfitRate());
    }

    private static Lottos createLottos(LottoGameMoney purchaseMoney) {
        final int manualLottoCount = InputView.getManualLottoCount();
        final LottoCount lottoCount = purchaseMoney.getPurchaseLottoCount(manualLottoCount);

        final List<Lotto> lottos = createManualLottos(manualLottoCount);
        lottos.addAll(createAutoLottos(lottoCount));
        OutputView.showPurchasedLottos(lottoCount, lottos);

        return new Lottos(lottos);
    }

    private static List<Lotto> createManualLottos(int lottoCount) {
        List<List<Integer>> manualLottoNumbers = InputView.getManualLottoNumbers(lottoCount);
        return LottoFactory.createLottos(manualLottoNumbers);
    }

    private static List<Lotto> createAutoLottos(LottoCount lottoCount) {
        return LottoFactory.createAutoLottos(lottoCount, new RandomLottoNumbersGenerator());
    }

    private static WinningLotto createWinningLotto() {
        final List<Integer> winningNumbers = InputView.getWinningLottoNumbers();
        Lotto winningLotto = LottoFactory.createLotto(winningNumbers);

        final int bonusNumber = InputView.getBonusBall();
        LottoNumber bonusLottoNumber = LottoNumber.valueOf(bonusNumber);

        return new WinningLotto(winningLotto, bonusLottoNumber);
    }
}
