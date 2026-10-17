package ua.kpi.comsys.dop;

public sealed interface Notification permits TextMsg, ImageMsg, AudioMsg {
}
