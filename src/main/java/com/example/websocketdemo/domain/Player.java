package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Player {
    private String name;
    private int level;
    boolean moving;

    private List<Door> hiddenDoors;

    private List<Treasure> hiddenTreasures;
    private List<Card> openedCard;

    public enum Classes{
//        WARRIOR(new Card(1,"Воин","Буйство: Можешь скинуть до 3 карт в бою, каждая даёт тебе + Бонус. При ничьей в бою ты побеждаешь.", true)),
//        WIZARD(new Card(2,"Волшебник","Заклинание Полёта: Можешь сбросить до 3 карт после броска на Смывку: каждая даёт +1 к Смывке. Заклинание Усмирения: Можешь сбросить всю \"руку\" (мин. 3 карты), чтобы усмирить одного монстра и не драться с ним ты получаешь только его Сокровища, но не Уровень. Если в бою участвуют другие монстры, с ним придётся воевать.", true)),
//        THIEF(new Card(3,"Вор","Подрезка: Можешь скинуть  карту, чтобы подрезать другого игрока (-2 в бою). Можешь делать это только один раз в отношении одной жертвы в одном бою; если 2 игрока совместно валят монстра, можешь подрезать их обоих. Кража: Можешь скинуть 1 карту, чтобы попытаться стырить маленькую шмотку у другого игрока. Брось кубик: 4 и больше - удачная кража, иначе тебя лупят, и ты теряешь  Уровень.", true)),
//        CLERIC(new Card(4,"Клирик","Воскрешение: Когда тебе надо вытянуть карты в открытую, ты можешь частично или полностью взять их соответствующего сброса. Затем ты должен сбросить с \"руки\" по  карте за каждую такую карту. Изгнание: можешь сбросить до 3 карт в бою против Андедов. Каждый сброс даёт тебе +3 Бонус.",true)),
        ;

        private Card card;

        Classes(Card card) {
            this.card = card;
        }
    }

    public enum Races{
//        DWARF(new Card(5,"Дварф", "Ты можешь нести любое количество Больших шмоток", true)),
//        ELF(new Card(6,"Эльф", "Поднимаешься на  уровень за каждого монстра, которого помогаешь убить.",true)),
//        HALFLING(new Card(7,"Халфлинг", "На каждом ходу можешь продать одну шмотку за двойную цену(цена других шмоток не меняется).",true)),
        ;

        private Card card;

        Races(Card card) {
            this.card = card;
        }
    }
}
