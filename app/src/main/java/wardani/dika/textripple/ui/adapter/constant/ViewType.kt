package wardani.dika.textripple.ui.adapter.constant

enum class ViewType(val value: Int) {
    DATA(1),
    LOADING_MORE(2);

    companion object {
        fun toViewType(value: Int): ViewType {
            var found = DATA
            for (viewType in values()) {
                if (viewType.value == value) {
                    found = viewType
                    break
                }
            }

            return found
        }
    }
}

