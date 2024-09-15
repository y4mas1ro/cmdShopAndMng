package shigeta;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CmdShop {
    // 商品情報の管理
    private Map<Integer, Item> items = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // 商品クラス
    class Item {
        private String name;
        private int stock;

        public Item(String name, int stock) {
            this.name = name;
            this.stock = stock;
        }

        public String getName() {
            return name;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        @Override
        public String toString() {
            return name + " " + stock + "個";
        }
    }

    // 商品の期値をセット
    public CmdShop() {
        items.put(1, new Item("りんご", 10));
        items.put(2, new Item("みかん", 10));
    }

    // メインメソッド
    public static void main(String[] args) {
        CmdShop system = new CmdShop();
        system.run();
    }

    // コマンドラインの実行
    public void run() {
        while (true) {
            System.out.println("管理コマンドライン: purchase / manage / exit");
            String command = scanner.nextLine();
            switch (command) {
                case "purchase":
                    purchaseMode();
                    break;
                case "manage":
                    manageMode();
                    break;
                case "exit":
                    System.out.println("システムを終了します。");
                    return;
                default:
                    System.out.println("無効なコマンドです。");
            }
        }
    }

    // 商品購入コマンドライン
    private void purchaseMode() {
        while (true) {
            System.out.println("商品購入コマンドライン: list / buy [商品ID] [購入数] / exit");
            String command = scanner.nextLine();
            if (command.equals("list")) {
                listAvailableItems();
            } else if (command.startsWith("buy")) {
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    int itemId = Integer.parseInt(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    buyItem(itemId, quantity);
                } else {
                    System.out.println("コマンド形式が間違っています。");
                }
            } else if (command.equals("exit")) {
                return;
            } else {
                System.out.println("無効なコマンドです。");
            }
        }
    }

    // 商品管理コマンドライン
    private void manageMode() {
        while (true) {
            System.out.println("商品管理コマンドライン: list / add [商品ID] [商品名] / modify [商品ID] [在庫数] / exit");
            String command = scanner.nextLine();
            if (command.equals("list")) {
                listAllItems();
            } else if (command.startsWith("add")) {
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    int itemId = Integer.parseInt(parts[1]);
                    String itemName = parts[2];
                    addItem(itemId, itemName);
                } else {
                    System.out.println("コマンド形式が間違っています。");
                }
            } else if (command.startsWith("modify")) {
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    int itemId = Integer.parseInt(parts[1]);
                    int stock = Integer.parseInt(parts[2]);
                    modifyItemStock(itemId, stock);
                } else {
                    System.out.println("コマンド形式が間違っています。");
                }
            } else if (command.equals("exit")) {
                return;
            } else {
                System.out.println("無効なコマンドです。");
            }
        }
    }

    // 商品購入機能/在庫がある商品のリストを表示
    private void listAvailableItems() {
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            Item item = entry.getValue();
            if (item.getStock() > 0) {
                System.out.println(entry.getKey() + " " + item);
            }
        }
    }

    // 商品購入機能/商品を購入
    private void buyItem(int itemId, int quantity) {
        Item item = items.get(itemId);
        if (item != null) {
            if (item.getStock() >= quantity) {
                item.setStock(item.getStock() - quantity);
                System.out.println(item.getName() + "を購入いただきありがとうございます。");
            } else {
                System.out.println(item.getName() + "の在庫が足りません。");
            }
        } else {
            System.out.println("商品ID " + itemId + " は存在しません。");
        }
    }

    // 商品管理機能/全商品をリスト表示
    private void listAllItems() {
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    // 商品管理機能/商品を追加
    private void addItem(int itemId, String itemName) {
        if (items.containsKey(itemId)) {
            System.out.println(itemId + "は既に利用されています。");
        } else {
            items.put(itemId, new Item(itemName, 0));
            System.out.println(itemName + "が追加されました。");
        }
    }

    // 商品管理機能/在庫数を変更
    private void modifyItemStock(int itemId, int stock) {
        if (stock < 0 || stock > 99) {
            System.out.println("在庫数が入力範囲外です。");
            return;
        }
        Item item = items.get(itemId);
        if (item != null) {
            item.setStock(stock);
            System.out.println(itemId + "の在庫数が" + stock + "に変更されました。");
        } else {
            System.out.println(itemId + "は存在しません。");
        }
    }
}