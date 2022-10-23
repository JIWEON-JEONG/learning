package TIL.spring.core.week3;


import org.hibernate.sql.Select;

//IDE - Intel , visual.

// 1. 부모타입을 자식타입으로 바꿀일이 있을까? : 당연히 바꿔서 쓰고, IDE
public  class Main {

    IDE ide;

    //실행해주는거죠.
    public void main(String[] args) {
        SelectIDE selectIDE = new SelectIDE();
        IDE ide1 = selectIDE.selectIntellij();
        Intellij ide2 = selectIDE.selectIntellij();
    }
}

//골라주는 애
class SelectIDE {
    Intellij selectIntellij() {
        return new Intellij();
    }

    IDE selectVisualStudio() {
        return new VisualStudio();
    }
}



// IDE 인터페이스
interface IDE{
    public void view();
}

// 인텔리제이 뷰해주는 친구
class Intellij implements IDE{

    @Override
    public void view() {

    }
}

//비쥬얼스튜디오 뷰해주는친구
class VisualStudio implements IDE{

    @Override
    public void view() {

    }
}
